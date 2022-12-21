package com.weiyang.component;

import com.weiyang.db.po.FlashSaleActivity;
import com.weiyang.db.dao.FlashSaleActivityDao;
import com.weiyang.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RedisPreheatRunner implements ApplicationRunner {
    @Autowired
    RedisService redisService;

    @Autowired
    FlashSaleActivityDao flashSaleActivityDao;

    /**
     * 啟動專案時，將庫存預熱到Redis
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<FlashSaleActivity> flashSaleActivities =
                flashSaleActivityDao.queryFlashSaleActivitysByStatus(1);
        for (FlashSaleActivity flashSaleActivity : flashSaleActivities) {
            redisService.setValue("stock:" + flashSaleActivity.getId(),
                    (long) flashSaleActivity.getAvailableStock());
        }
    }

}
