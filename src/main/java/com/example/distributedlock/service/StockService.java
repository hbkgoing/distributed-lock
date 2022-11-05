package com.example.distributedlock.service;

import com.example.distributedlock.pojo.Stock;
import org.springframework.stereotype.Service;

/**
 * Description 处理仓库数据
 * Author hebaokai
 * Date 2022/11/5 17:35
 *
 * @Version 1.0
 **/

@Service
public class StockService {

    //直接在这里new一个Stock。service初始化的时候会直接new出来一个成员变量实例。
    private Stock stock = new Stock();

    public synchronized void  deDuct() {
        stock.setStock(stock.getStock()-1);
        System.out.println("库存余量："+ stock.getStock());
    }

}
