package com.weiyang.db.mappers;

import com.weiyang.db.po.FlashSaleActivity;

import java.util.List;

public interface FlashSaleActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlashSaleActivity record);

    int insertSelective(FlashSaleActivity record);

    FlashSaleActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlashSaleActivity record);

    int updateByPrimaryKey(FlashSaleActivity record);

    List<FlashSaleActivity> queryFlashSaleActivitysByStatus(int activityStatus);

    int lockStock(Long flashSaleActivityId);

    int deductStock(Long flashSaleActivityId);
}