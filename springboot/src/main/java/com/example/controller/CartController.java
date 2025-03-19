package com.example.controller;

import com.example.common.Result;
import com.example.entity.Cart;
import com.example.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

// 购物车相关接口
@RestController
@RequestMapping(value = "/cart") // 为该控制器的所有接口统一设置路径前缀
public class CartController {
    // 使用注解将服务类注入控制器，用于对数据库进行操作
    @Resource
    private CartService cartService;

    // 新增购物车 -> 对应前台商品详情页面将商品添加购物车按钮
    @PostMapping // 接收/cart请求
    public Result add(@RequestBody Cart cart) {
        cartService.add(cart);
        return Result.success(cart);
    }

    // 根据id删除购物车中商品 -> 对应前台购物车页面删除-确定按钮
    @DeleteMapping("/{id}") // 接收/cart/{id}请求
    public Result delete(@PathVariable Integer id) {
        cartService.delete(id);
        return Result.success();
    }

//    // 更新购物车
//    @PutMapping // 接收/cart请求
//    public Result update(@RequestBody Cart cart) {
//        cartService.update(cart);
//        return Result.success();
//    }

//    // 根据id查询
//    @GetMapping("/{id}") // 接收/cart/{id}请求
//    public Result detail(@PathVariable Integer id) {
//        Cart cart = cartService.findById(id);
//        return Result.success(cart);
//    }

    // 查询所有购物车中的商品 -> 对应前台购物车页面加载购物车中的商品
    @GetMapping // 接收/cart请求
    public Result all() {
        return Result.success(cartService.findAll());
    }

//    // 分页查询
//    @PostMapping("/page") // 接收/cart/page请求
//    public Result page(@RequestBody Cart search,
//                       @RequestParam(defaultValue = "1") Integer pageNum,
//                       @RequestParam(defaultValue = "5") Integer pageSize) {
//        return Result.success(cartService.findPage(search, pageNum, pageSize));
//    }
}
