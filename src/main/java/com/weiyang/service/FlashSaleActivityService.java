package com.weiyang.service;

import com.alibaba.fastjson.JSON;
import com.weiyang.component.RocketMQService;
import com.weiyang.db.dao.OrderDao;
import com.weiyang.db.po.FlashSaleActivity;
import com.weiyang.db.dao.FlashSaleActivityDao;
import com.weiyang.db.po.Order;
import com.weiyang.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class FlashSaleActivityService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private FlashSaleActivityDao flashSaleActivityDao;

    @Autowired
    private RocketMQService rocketMQService;

    @Autowired
    private OrderDao orderDao;

    /**
     * datacenterId; 數據中心
     * machineId; 機器標識
     * 在分佈式環境中可以從機器配置上讀取
     * 單機開發環境中先寫死
     */
    private SnowFlake snowFlake = new SnowFlake(1, 1);

    /**
     * 創建訂單
     *
     * @param flashSaleActivityId
     * @param userId
     * @return
     * @throws Exception
     */
    public Order createOrder(long flashSaleActivityId, long userId) throws Exception {
        /*
         * 1.創建訂單
         */
        FlashSaleActivity flashSaleActivity = flashSaleActivityDao.queryFlashSaleActivityById(flashSaleActivityId);
        Order order = new Order();
        //採用雪花演算法生成訂單ID
        order.setOrderNo(String.valueOf(snowFlake.nextId()));
        order.setFlashsaleActivityId(flashSaleActivity.getId());
        order.setUserId(userId);
        order.setOrderAmount(flashSaleActivity.getFlashSalePrice().longValue());
        /*
         *2.發送創建訂單消息
         */
        rocketMQService.sendMessage("flash_sale_order", JSON.toJSONString(order));
        return order;
    }


    /**
     * 判斷是否還有庫存
     *
     * @param activityId 商品ID
     * @return
     */
    public boolean flashSaleStockValidator(long activityId) {
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }

    /**
     * 訂單支付完成處理
     *
     * @param orderNo
     */
    public void payOrderProcess(String orderNo) {
        log.info("完成支付訂單 訂單號：" + orderNo);
        Order order = orderDao.queryOrder(orderNo);
        boolean deductStockResult =
                flashSaleActivityDao.deductStock(order.getFlashsaleActivityId());
        if (deductStockResult) {
            order.setPayTime(new Date());
            // 訂單狀態 0、沒有可用庫存，無效訂單 1、已創建等待支付 2、完成支付
            order.setOrderStatus(2);
            orderDao.updateOrder(order);
        }
    }


}
