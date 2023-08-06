package com.itheima.domain;

import java.io.Serializable;
import java.util.List;

public class Orders implements Serializable {
    private Integer id;
    private Integer customerId;
    private Float totalPrice;

    //一对多映射
    private List<Goods> goodsList;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

