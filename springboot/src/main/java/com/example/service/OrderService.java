package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

import com.example.dao.*;
import com.example.common.ResultCode;
import com.example.common.config.JwtTokenUtils;
import com.example.entity.*;
import com.example.exception.CustomException;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 服务层的实现类，主要负责订单相关的业务逻辑
@Service
public class OrderService {
    // 使用注解将Dao注入到服务类中，用于与数据库进行交互
    @Resource
    private OrderDao orderDao;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private CartDao cartDao;
    @Resource
    private UserDao userDao;
    @Resource
    private AddressDao addressDao;

    // 添加订单（直接下单购买）
    public void addOne(Order order) {
        // 获取当前登录用户
        Account currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CustomException("-1", "登录失效，请重新登录");
        }

        User user = userDao.selectByPrimaryKey(currentUser.getId());
        if (user.getAccount() < order.getPrice()) {
            throw new CustomException("-1", "您的余额不足，请到个人中心充值");
        }
        order.setOrderId(DateUtil.format(new Date(), "yyyyMMddhhmmss"));
        order.setTime(DateUtil.now());
        order.setStatus("待发货");
        orderDao.insertSelective(order);
        Double account = user.getAccount();
        user.setAccount(account - order.getPrice());
        userDao.updateByPrimaryKeySelective(user);
    }

    // 添加订单（从购物车中下单）
    public void add(OrderDto orderDto) {
        List<Cart> cartList = orderDto.getList();
        Integer addressId = orderDto.getAddressId();
        // 获取当前登录用户
        Account currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CustomException("-1", "登录失效，请重新登录");
        }
        // 计算总价格
        Double totalPrice = calculateTotalPrice(cartList);
        // 检查用户的余额
        User user = userDao.selectByPrimaryKey(currentUser.getId());
        if (user.getAccount() < totalPrice) {
            throw new CustomException("-1", "您的余额不足，请到个人中心充值");
        }
        // 遍历购物车列表，为每个商品创建一个订单对象
        for (Cart cart : cartList) {
            Order order = new Order();
            order.setOrderId(DateUtil.format(new Date(), "yyyyMMddhhmmss"));
            order.setTime(DateUtil.now());
            order.setUserId(currentUser.getId());

            Goods goods = goodsDao.selectByPrimaryKey(cart.getGoodsId());
            order.setGoodsId(goods.getId());
            order.setNum(cart.getNum());
            order.setPrice(goods.getPrice() * cart.getNum());
            order.setStatus("待发货");
            order.setAddressId(addressId);

            orderDao.insertSelective(order);

            // 减少对应商品的库存
            Integer num = cart.getNum();
            Integer goodsNum = goods.getGoodsnum();
            goods.setGoodsnum(goodsNum-num);
            goodsDao.updateByPrimaryKeySelective(goods);

            // 删除购物车中该数据
            cartDao.deleteByPrimaryKey(cart.getId());
        }
        // 减少用户余额
        Double account = user.getAccount();
        user.setAccount(account-totalPrice);
        userDao.updateByPrimaryKeySelective(user);
    }

    // 计算购物车中商品的总价格
    private Double calculateTotalPrice(List<Cart> cartList) {
        double total = 0;
        // 遍历购物车列表，计算购物车中商品的总价格
        for (Cart cart : cartList) {
            total += cart.getPrice() * cart.getNum();
        }
        return total;
    }

    // 删除订单
    public void delete(Integer id) {
        orderDao.deleteByPrimaryKey(id);
    }

    // 更新订单
    public void update(Order order) {
        //如果当前状态是退款申请中，更新状态是退款完成，退款给用户。
        Integer id = order.getId();
        Order orderNow = orderDao.selectByPrimaryKey(id);
        String status = orderNow.getStatus();
        // 当前状态是退款中，更新后是退款完成，则退款给用户，并退库存
        if (("退款申请中".equals(status)) && ("退款完成".equals(order.getStatus()))){
            Double price = order.getPrice();
            Integer userId = order.getUserId();
            User user = userDao.selectByPrimaryKey(userId);
            // 退款
            if (ObjectUtil.isNotNull(user)){
                Double account = user.getAccount();
                user.setAccount(account + price);
                userDao.updateByPrimaryKeySelective(user);
            }
            // 退库存
            Integer goodsId = order.getGoodsId();
            Goods goods = goodsDao.selectByPrimaryKey(goodsId);
            if (ObjectUtil.isNotNull(goods)){
                Integer goodsnum = goods.getGoodsnum();
                goods.setGoodsnum(goodsnum + order.getNum());
                goodsDao.updateByPrimaryKeySelective(goods);
            }
        }
        orderDao.updateByPrimaryKeySelective(order);
    }

    // 根据id查询订单
    public Order findById(Integer id) {
        return orderDao.selectByPrimaryKey(id);
    }

    // 查询所有订单
    public List<Order> findAll() {
        Account currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CustomException("-1", "登录失效，请重新登录");
        }
        List<Order> list = orderDao.findByUserId(currentUser.getId());
        extracted(list);
        return list;
    }

    // 分页查询订单
    public PageInfo<Order> findPage(Order search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查找所有满足条件的商品
        List<Order> orderList = findByCondition(user, search);
        // 填充地址信息
        for (Order order : orderList) {
            Address address = addressDao.selectByPrimaryKey(order.getAddressId());
            if (ObjectUtil.isNotEmpty(address)){
                order.setName(address.getUsername());
                order.setAddress(address.getAddress());
                order.setPhone(address.getPhone());
            }
        }
        // 封装成PageInfo对象返回
        return PageInfo.of(orderList);
    }

    // 根据条件查询订单
    public List<Order> findByCondition(Account user, Order search) {
        List<Order> list = orderDao.findBySearch(search);
        extracted(list);
        return list;
    }

    // 柱状图查询所有订单
    public List<Order> findBarAll() {
        List<Order> list = orderDao.selectAll();
        extracted(list);
        return list;
    }

    // 饼状图查询所有订单
    public List<Order> findBieAll() {
        List<Order> list = orderDao.selectAll();
        extracted(list);
        return list;
    }

    // 填充订单信息
    private void extracted(List<Order> orderList) {
        // 遍历订单列表
        for (Order order : orderList) {
            // 根据用户id查询用户信息并填充到订单对象中
            User user = userDao.selectByPrimaryKey(order.getUserId());
            if (ObjectUtil.isNotEmpty(user)){
                order.setUserName(user.getUsername());
            }
            // 处理订单中的商品
            Goods goods = goodsDao.selectByPrimaryKey(order.getGoodsId());
            if (ObjectUtil.isNotEmpty(goods)){
                goods.setNum(order.getNum());
                order.setGoods(goods);
            }
            // 处理收货
            Address address = addressDao.selectByPrimaryKey(order.getAddressId());
            if (ObjectUtil.isNotEmpty(address)){
                order.setName(address.getUsername());
                order.setAddress(address.getAddress());
                order.setPhone(address.getPhone());
            }
        }
    }
}