package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.Stock;

public interface StockMapper extends BaseMapper<Stock> {

    int deduct(@Param("id") long id);

    int getCount(@Param("id") long id);
}
