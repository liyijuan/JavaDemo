package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Stock;
import org.example.mapper.StockMapper;
import org.example.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

//没有在StockMapper中extendBaseMapper导致这一段初始一直报错，需要实现接口中的所有方法
@Slf4j
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

//    未在启动类上添加@MapperScan(包路径)时，使用@Autowired注入会报错
    @Autowired
    public StockMapper stockMapper;

    @Autowired
    public RedisTemplate<String, Object> redisTemplates;

    @Override
    public int deduct(long id) {
//        扣减库存前判断库存是否充足，从缓存中获取
//        后面再考虑这个有效期，先整个简单的
        String key = "stock".concat(String.valueOf(id)).concat("count");
//        缓存中可以获取到，判断缓存中的库存是否充足，不充足则直接返回。这个方案后面再想一下
        Integer count = (Integer) redisTemplates.opsForValue().get(key);
        if (count != null && count <= 0) {
            log.debug("库存不足，count={}", count);
            return -1;
        }
//        缓存中没有，直接查询数据库
        count = stockMapper.getCount(id);
        if (count > 0) {
//            更新数据库后重建缓存
            int deduct = stockMapper.deduct(id);
            redisTemplates.opsForValue().set(key, count - 1);
            return deduct;
        }
        return -1;
    }

    @Override
    public int getCountById(long id) {
        String key = "stock".concat(String.valueOf(id)).concat("count");
        Integer count = (Integer) redisTemplates.opsForValue().get(key);
        if (count != null) {
            return count;
        }
//        查询数据库更新缓存
        count = stockMapper.getCount(id);
        redisTemplates.opsForValue().set(key, count);
        return count;
    }

    @Override
    public int getCountNoCache(long id) {
        int count = stockMapper.getCount(id);
        return count;
    }
}
