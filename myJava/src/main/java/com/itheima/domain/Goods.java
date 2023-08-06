package com.itheima.domain;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {

    private Integer id;
    private String goodName;
    private Float price;

    //一对多映射
    private List<Orders> ordersList;

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodName='" + goodName + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}


