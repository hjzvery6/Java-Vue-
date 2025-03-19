package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.example.common.ResultCode;
import com.example.common.config.JwtTokenUtils;
import com.example.dao.CartDao;
import com.example.dao.GoodsDao;
import com.example.entity.Account;
import com.example.entity.Cart;
import com.example.entity.Goods;
import com.example.exception.CustomException;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

// 服务层实现类，负责实现购物车相关的业务逻辑
@Service
public class CartService {
    // 注入CartDao和GoodsDao，用于访问数据库
    @Resource
    private CartDao cartDao;
    @Resource
    private GoodsDao goodsDao;

    // 购物车中新增一个商品
    public Cart add(Cart cart) {
        cartDao.insertSelective(cart);
        return cart;
    }

    // 删除一个购物车中的商品
    public void delete(Integer id) {
        cartDao.deleteByPrimaryKey(id);
    }

    // 更新购物车中的商品信息
    public void update(Cart cart) {
        cartDao.updateByPrimaryKeySelective(cart);
    }

    // 根据购物车ID查找购物车中的商品
    public Cart findById(Integer id) {
        return cartDao.selectByPrimaryKey(id);
    }

    // 查找购物车中的所有商品
    public List<Cart> findAll() {
        // 获取当前登录用户
        Account currentUser = JwtTokenUtils.getCurrentUser();
        // 根据当前用户的id获取它的购物车列表
        List<Cart> cartList = cartDao.findByUserId(currentUser.getId());
        // 为购物车中每个商品设置信息
        for (Cart cart : cartList) {
            Goods goods = goodsDao.selectByPrimaryKey(cart.getGoodsId());
            cart.setImg(goods.getImg());
            cart.setGoodsName(goods.getName());
            cart.setPrice(goods.getPrice());
        }
        return cartList;
    }

    // 分页查找购物车中的商品
    public PageInfo<Cart> findPage(Cart search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查找所有满足条件的商品
        List<Cart> all = findByCondition(user, search);
        // 封装成PageInfo对象返回
        return PageInfo.of(all);
    }

    // 按照条件查找购物车中的商品
    public List<Cart> findByCondition(Account user, Cart search) {
		return cartDao.findBySearch(search);
    }
}