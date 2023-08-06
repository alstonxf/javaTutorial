package com.itheima.dao;

import com.itheima.domain.Goods;

public interface IGoodsDao {

    /**
     * 根据 id 查询商品
     */
    public Goods findGoodsById(Integer id);

}

