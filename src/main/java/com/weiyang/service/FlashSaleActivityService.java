package com.weiyang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleActivityService {
    @Autowired
    private RedisService redisService;

    /**判斷是否還有庫存
     * @param activityId 商品ID
     * @return
     */
    public boolean flashSaleStockValidator(long activityId) {
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }

}
