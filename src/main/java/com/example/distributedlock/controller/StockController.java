package com.example.distributedlock.controller;

import com.example.distributedlock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description TODO
 * Author hebaokai
 * Date 2022/11/5 17:36
 *
 * @Version 1.0
 **/
@RestController
public class StockController {


    @Autowired
    private StockService stockService;

    @GetMapping("stock/deduct")
    public String deduct() {
        stockService.deDuct();
        return "hello stock --";
    }
}
