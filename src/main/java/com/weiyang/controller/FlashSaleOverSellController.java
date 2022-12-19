package com.weiyang.controller;

import com.weiyang.service.FlashSaleActivityService;
import com.weiyang.service.FlashSaleOverSellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlashSaleOverSellController {
    @Autowired
    private FlashSaleOverSellService flashSaleOverSellService;
    @Autowired
    private FlashSaleActivityService flashSaleActivityService;

//    /**
//     * 簡單 處理搶購請求 * @param flashsaleActivityId * @return
//     */
//    @ResponseBody
//    @RequestMapping("/flashsale/{flashSaleActivityId}")
//    public String flashSale(@PathVariable long flashSaleActivityId) {
//        return flashSaleOverSellService.processFlashSale(flashSaleActivityId);
//    }

    /**
     * 使用lua script處理搶購請求
     *
     * @param flashSaleActivityId
     * @return
     */
    @ResponseBody
    @GetMapping("/flashsale/{flashSaleActivityId}")
    public String flashSaleCommodity(@PathVariable long flashSaleActivityId) {
        boolean stockValidateResult =
                flashSaleActivityService.flashSaleStockValidator(flashSaleActivityId);
        return stockValidateResult ? "恭喜搶購成功" : "商品已完售";
    }

}
