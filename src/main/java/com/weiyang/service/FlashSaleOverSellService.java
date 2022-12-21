package com.weiyang.service;

import com.weiyang.db.po.FlashSaleActivity;
import com.weiyang.db.dao.FlashSaleActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleOverSellService {
    @Autowired
    FlashSaleActivityDao flashSaleActivityDao;

    public String processFlashSale(long activityId) {

        FlashSaleActivity flashSaleActivity =
                flashSaleActivityDao.queryFlashSaleActivityById(activityId);
        long availableStock = flashSaleActivity.getAvailableStock();
        String result;
        if (availableStock > 0) {
            result = "恭喜，搶購成功";
            System.out.println(result);
            availableStock = availableStock - 1;
            flashSaleActivity.setAvailableStock((int) availableStock);
            flashSaleActivityDao.updateFlashSaleActivity(flashSaleActivity);
        }else{
            result = "很抱歉，搶購失敗";
            System.out.println(result);
        }
        return result;
    }
}
