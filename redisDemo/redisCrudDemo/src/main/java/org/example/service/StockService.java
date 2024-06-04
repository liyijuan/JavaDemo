package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Stock;

public interface StockService extends IService<Stock> {
    int deduct(long id);

    int getCountById(long id);

    int getCountNoCache(long id);
}
