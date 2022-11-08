package com.example.distributedlock.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.distributedlock.mapper.StockMapper;
import com.example.distributedlock.pojo.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    StringRedisTemplate redisTemplate;

    public void deDuct() {
        // 1. 通过setnx 来获取锁 添加过期时间防止死锁
        // 根据具体的业务执行时间，自动续期lock
//        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "0909",10, TimeUnit.SECONDS);
//        if (!lock) {
//            while (!lock) {
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                lock = redisTemplate.opsForValue().setIfAbsent("lock", "0909");
//            }
//        }
        String uuid = String.valueOf(UUID.randomUUID());
        while (!redisTemplate.opsForValue().setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS)) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Stock stock = stockMapper.selectOne(new LambdaQueryWrapper<Stock>().eq(Stock::getProductCode, "10010"));
        Integer value = Integer.parseInt(stock.getStock());
        stock.setStock(String.valueOf(value - 1));
        stockMapper.updateById(stock);

        //两个redis操作不能保证原子性
//        if (uuid.equals(redisTemplate.opsForValue().get("lock"))) {
//            redisTemplate.delete("lock");
//        }
//        利用lua脚本来将两条指令合并为一条
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] "+
                "then "+
                "   return redis.call('del',KEYS[1]) " +
                "else " +
                "   return 0 " +
                "end";
        redisTemplate.execute(new DefaultRedisScript<>(luaScript, Boolean.class), Arrays.asList("lock"),uuid);

    }

}
