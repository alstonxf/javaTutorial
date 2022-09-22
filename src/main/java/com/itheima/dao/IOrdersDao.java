package com.itheima.dao;

import com.itheima.domain.Orders;

public interface IOrdersDao {

    /**
     * 嵌套查询方式：根据订单Id查询当前订单所对应的商品信息
     */
    public Orders findOrdersNestedQueryByOrdersId(Integer id);

    /**
     * 嵌套结果：根据订单Id查询当前订单所对应的商品信息
     */
    public Orders findOrdersNestedResultsByOrdersId(Integer id);

}

