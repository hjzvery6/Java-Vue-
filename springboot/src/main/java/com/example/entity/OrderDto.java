package com.example.entity;

import java.util.List;

// 这是一个数据传输对象，用于封装订单相关的数据
public class OrderDto {

    private List<Cart> list; // 存储与订单相关联的购物车商品列表
    private Integer addressId; // 存储当前订单相关的收获地址id

    // 下面是两个字段的get和set方法
    public List<Cart> getList() {
        return list;
    }
    public void setList(List<Cart> list) {
        this.list = list;
    }

    public Integer getAddressId() {
        return addressId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
}
