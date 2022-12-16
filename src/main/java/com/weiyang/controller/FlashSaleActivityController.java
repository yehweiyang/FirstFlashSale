package com.weiyang.controller;

import com.weiyang.db.po.FlashSaleActivity;
import com.weiyang.db.po.FlashSaleCommodity;
import com.weiyang.db.po.mappers.dao.FlashSaleActivityDao;
import com.weiyang.db.po.mappers.dao.FlashSaleCommodityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class FlashSaleActivityController {

    @Autowired
    private FlashSaleActivityDao flashSaleActivityDao;

    @Autowired
    private FlashSaleCommodityDao flashSaleCommodityDao;

    @RequestMapping("/addFlashSaleActivity")
    public String addFlashSaleActivity() {
        return "add_activity";
    }

    @RequestMapping("/addFlashSaleActivityAction")
    public String addFlashSaleActivityAction(
            @RequestParam("name") String name,
            @RequestParam("commodityId") long commodityId,
            @RequestParam("flashSalePrice") BigDecimal flashSalePrice,
            @RequestParam("oldPrice") BigDecimal oldPrice,
            @RequestParam("flashSaleNumber") long flashSaleNumber,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            Map<String, Object> resultMap
    ) throws ParseException {
        startTime = startTime.substring(0, 10) + startTime.substring(11);
        endTime = endTime.substring(0, 10) + endTime.substring(11);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddhh:mm");
        FlashSaleActivity flashSaleActivity = new FlashSaleActivity();
        flashSaleActivity.setName(name);
        flashSaleActivity.setCommodityId(commodityId);
        flashSaleActivity.setFlashSalePrice(flashSalePrice);
        flashSaleActivity.setOldPrice(oldPrice);
        flashSaleActivity.setTotalStock(flashSaleNumber);
        flashSaleActivity.setAvailableStock(new Integer("" + flashSaleNumber));
        flashSaleActivity.setLockStock(0L);
        flashSaleActivity.setActivityStatus(1);
        flashSaleActivity.setStartTime(format.parse(startTime));
        flashSaleActivity.setEndTime(format.parse(endTime));
        flashSaleActivityDao.inertFlashSaleActivity(flashSaleActivity);
        resultMap.put("flashSaleActivity", flashSaleActivity);
        return "add_success";
    }


    @GetMapping("/flashSales")
    public String activityList(Map<String, Object> resultMap) {
        List<FlashSaleActivity> flashSaleActivities = flashSaleActivityDao.queryFlashSaleActivitysByStatus(1);
        resultMap.put("flashSaleActivities", flashSaleActivities);
        return "flashsale_activity";
    }

    @GetMapping("/item/{flashSaleActivityId}")
    public String itemPage(@PathVariable("flashSaleActivityId") long flashSaleActivityId, Map<String, Object> resultMap) {

        FlashSaleActivity flashSaleActivity = flashSaleActivityDao.queryFlashSaleActivityById(flashSaleActivityId);

        FlashSaleCommodity flashSaleCommodity = flashSaleCommodityDao.queryFlashSaleCommodityById(flashSaleActivity.getCommodityId());

        resultMap.put("flashSaleActivity", flashSaleActivity);
        resultMap.put("flashSaleCommodity", flashSaleCommodity);

        resultMap.put("flashSalePrice", flashSaleActivity.getFlashSalePrice());
        resultMap.put("oldPrice", flashSaleActivity.getOldPrice());
        resultMap.put("commodityId", flashSaleActivity.getCommodityId());
        resultMap.put("commodityName", flashSaleCommodity.getCommodityName());
        resultMap.put("commodityDesc", flashSaleCommodity.getCommodityDesc());

        return "flashsale_item";
    }
}
