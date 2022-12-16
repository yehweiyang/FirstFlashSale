package com.weiyang.db.mappers;

import com.weiyang.db.po.FlashSaleCommodity;

public interface FlashSaleCommodityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlashSaleCommodity record);

    int insertSelective(FlashSaleCommodity record);

    FlashSaleCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlashSaleCommodity record);

    int updateByPrimaryKey(FlashSaleCommodity record);
}