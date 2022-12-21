package com.weiyang.db.dao;

import com.weiyang.db.mappers.FlashSaleActivityMapper;
import com.weiyang.db.po.FlashSaleActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Repository
public class FlashSaleActivityDaoImpl implements FlashSaleActivityDao {

    @Resource
    private FlashSaleActivityMapper flashSaleActivityMapper;

    @Override
    public List<FlashSaleActivity> queryFlashSaleActivitysByStatus(int activityStatus) {
        return flashSaleActivityMapper.queryFlashSaleActivitysByStatus(activityStatus);
    }

    @Override
    public void inertFlashSaleActivity(FlashSaleActivity flashSaleActivity) {
        flashSaleActivityMapper.insert(flashSaleActivity);
    }

    @Override
    public FlashSaleActivity queryFlashSaleActivityById(long activityId) {
        return flashSaleActivityMapper.selectByPrimaryKey(activityId);
    }

    @Override
    public void updateFlashSaleActivity(FlashSaleActivity flashSaleActivity) {
        flashSaleActivityMapper.updateByPrimaryKey(flashSaleActivity);
    }

    @Override
    public boolean lockStock(Long flashSaleActivityId) {
        int result = flashSaleActivityMapper.lockStock(flashSaleActivityId);
        if (result < 1) {
            log.error("鎖定庫存失敗");
            return false;
        }
        return true;
    }

    @Override
    public boolean deductStock(Long flashSaleActivityId) {
        int result = flashSaleActivityMapper.deductStock(flashSaleActivityId);
        if (result < 1) {
            log.error("扣減庫存失敗");
            return false;
        }
        return true;
    }




}
