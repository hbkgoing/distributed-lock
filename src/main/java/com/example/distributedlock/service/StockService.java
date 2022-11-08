package com.example.distributedlock.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.distributedlock.mapper.StockMapper;
import com.example.distributedlock.pojo.Stock;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    StockMapper stockMapper;

    public synchronized void  deDuct() {
        Stock stock = stockMapper.selectOne(new LambdaQueryWrapper<Stock>().eq(Stock::getProductCode, "10010"));
        Integer value = Integer.parseInt(stock.getStock());
        stock.setStock(String.valueOf(value-1));
//        System.out.println("库存余量："+ stock.getStock());
        stockMapper.updateById(stock);
    }

}
