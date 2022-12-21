package com.weiyang.controller;

import com.weiyang.db.dao.OrderDao;
import com.weiyang.db.po.FlashSaleActivity;
import com.weiyang.db.po.FlashSaleCommodity;
import com.weiyang.db.dao.FlashSaleActivityDao;
import com.weiyang.db.dao.FlashSaleCommodityDao;
import com.weiyang.db.po.Order;
import com.weiyang.service.FlashSaleActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
public class FlashSaleActivityController {

    @Autowired
    private FlashSaleActivityService flashSaleActivityService;

    @Autowired
    private FlashSaleActivityDao flashSaleActivityDao;

    @Autowired
    private FlashSaleCommodityDao flashSaleCommodityDao;

    @Autowired
    OrderDao orderDao;


    /**
     * 訂單支付
     * @return
     */
    @RequestMapping("/flashsale/payOrder/{orderNo}")
    public String payOrder(@PathVariable String orderNo) throws Exception {
        flashSaleActivityService.payOrderProcess(orderNo);
        return "redirect:/flashsale/orderQuery/" + orderNo;
    }





    /**
     * 訂單查詢
     *
     * @param orderNo
     * @return
     */
    @RequestMapping("/flashsale/orderQuery/{orderNo}")
    public ModelAndView orderQuery(@PathVariable String orderNo) {
        log.info("訂單查詢，訂單號：" + orderNo);
        Order order = orderDao.queryOrder(orderNo);
        ModelAndView modelAndView = new ModelAndView();
        if (order != null) {
            modelAndView.setViewName("order");
            modelAndView.addObject("order", order);
            FlashSaleActivity flashSaleActivity =
                    flashSaleActivityDao.queryFlashSaleActivityById(order.getFlashsaleActivityId());
            modelAndView.addObject("flashSaleActivity", flashSaleActivity);
        } else {
            modelAndView.setViewName("order_wait");
        }
        return modelAndView;
    }


    /**
     * 處理搶購請求
     *
     * @param userId
     * @param flashSaleActivityId
     * @return
     */
    @RequestMapping("/flashsale/buy/{userId}/{flashSaleActivityId}")
    public ModelAndView flashSaleCommodity(@PathVariable long userId, @PathVariable long flashSaleActivityId) {
        boolean stockValidateResult = false;
        ModelAndView modelAndView = new ModelAndView();
        try {
            /*
             * 確認是否能夠進行秒殺
             */
            stockValidateResult = flashSaleActivityService.flashSaleStockValidator(flashSaleActivityId);
            if (stockValidateResult) {
                Order order = flashSaleActivityService.createOrder(flashSaleActivityId, userId);
                modelAndView.addObject("resultInfo", "秒殺成功，訂單創建中，訂單ID：" + order.getOrderNo());
                modelAndView.addObject("orderNo", order.getOrderNo());
            } else {
                modelAndView.addObject("resultInfo", "對不起，商品庫存不足");
            }
        } catch (Exception e) {
            log.error("秒殺系統異常" + e.toString());
            modelAndView.addObject("resultInfo", "秒殺失敗");
        }
        modelAndView.setViewName("flash_sale_result");
        return modelAndView;
    }


    @RequestMapping("/addFlashSaleActivity")
    public String addFlashSaleActivity() {
        return "add_activity";
    }

    @RequestMapping("/addFlashSaleActivityAction")
    public String addFlashSaleActivityAction(@RequestParam("name") String name, @RequestParam("commodityId") long commodityId, @RequestParam("flashSalePrice") BigDecimal flashSalePrice, @RequestParam("oldPrice") BigDecimal oldPrice, @RequestParam("flashSaleNumber") long flashSaleNumber, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, Map<String, Object> resultMap) throws ParseException {
        startTime = startTime.substring(0, 10) + startTime.substring(11);
        endTime = endTime.substring(0, 10) + endTime.substring(11);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddhh:mm", Locale.getDefault(Locale.Category.FORMAT));
        FlashSaleActivity flashSaleActivity = new FlashSaleActivity();
        flashSaleActivity.setName(name);
        flashSaleActivity.setCommodityId(commodityId);
        flashSaleActivity.setFlashSalePrice(flashSalePrice);
        flashSaleActivity.setOldPrice(oldPrice);
        flashSaleActivity.setTotalStock(flashSaleNumber);
        flashSaleActivity.setAvailableStock((int) flashSaleNumber);
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
        return "flash_sale_activity";
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

        return "flash_sale_item";
    }
}
