package com.weiyang.db.dao;

import com.weiyang.db.mappers.FlashSaleCommodityMapper;
import com.weiyang.db.po.FlashSaleCommodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class FlashSaleCommodityDaoImpl implements FlashSaleCommodityDao {

    @Resource
    private FlashSaleCommodityMapper flashSaleCommodityMapper;

    @Override
    public FlashSaleCommodity queryFlashSaleCommodityById(long commodityId) {
        return flashSaleCommodityMapper.selectByPrimaryKey(commodityId);
    }
}
