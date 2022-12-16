package com.weiyang.db.po.mappers.dao;


import com.weiyang.db.po.FlashSaleActivity;

import java.util.List;

public interface FlashSaleActivityDao {

    public List<FlashSaleActivity> queryFlashSaleActivitysByStatus(int activityStatus);

    public void inertFlashSaleActivity(FlashSaleActivity flashSaleActivity);

    public FlashSaleActivity queryFlashSaleActivityById(long activityId);

    public void updateFlashSaleActivity(FlashSaleActivity flashSaleActivity);
}
