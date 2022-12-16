package com.weiyang.db.po.mappers.dao;

import com.weiyang.db.mappers.FlashSaleActivityMapper;
import com.weiyang.db.po.FlashSaleActivity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

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
}
