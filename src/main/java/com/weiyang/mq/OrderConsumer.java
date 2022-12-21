package com.weiyang.mq;

import com.alibaba.fastjson.JSON;
import com.weiyang.db.dao.FlashSaleActivityDao;
import com.weiyang.db.dao.OrderDao;
import com.weiyang.db.po.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RocketMQMessageListener(topic = "flash_sale_order", consumerGroup =
        "flash_sale_order_group")
public class OrderConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private FlashSaleActivityDao flashSaleActivityDao;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        //1.解析創建訂單請求消息
        String message = new String(messageExt.getBody(),
                StandardCharsets.UTF_8);
        log.info("接收到創建訂單請求：" + message);
        Order order = JSON.parseObject(message, Order.class);
        order.setCreateTime(new Date());
        //2.扣減庫存
        boolean lockStockResult =
                flashSaleActivityDao.lockStock(order.getFlashsaleActivityId());
        if (lockStockResult) {
            //訂單狀態 0:沒有可用庫存，無效訂單 1:已創建等待付款
            order.setOrderStatus(1);
        } else {
            order.setOrderStatus(0);
        }
        //3.插入訂單
        orderDao.insertOrder(order);
    }
}

