package com.example.controller;

import com.example.common.Result;
import com.example.entity.*;
import com.example.service.*;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

// 商品管理相关接口
@RestController
@RequestMapping(value = "/goods") // 为该控制器的所有接口统一设置路径前缀
public class GoodsController {
    // 使用注解将服务类注入到控制器
    @Resource
    private GoodsService goodsService;

    // 新增商品 -> 对应后台商品信息管理和热销商品管理里面的新增-保存按钮
    @PostMapping // 接收/goods请求
    public Result add(@RequestBody Goods goods) {
        goodsService.add(goods);
        return Result.success(goods);
    }

    // 根据id删除商品 -> 对应后台商品信息管理和热销商品管理里面的删除-确定按钮
    @DeleteMapping("/{id}") // 接收/goods/{id}请求
    public Result delete(@PathVariable Integer id) {
        goodsService.delete(id);
        return Result.success();
    }

    // 更新商品信息 -> 对应后台商品信息管理和热销商品管理里面的编辑-保存按钮
    @PutMapping // 接收/goods请求
    public Result update(@RequestBody Goods goods) {

        goodsService.update(goods);
        return Result.success();
    }

    // 根据id查询商品 -> 对应前台商品详情页的这个商品的展示
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Goods goods = goodsService.findById(id);
        return Result.success(goods);
    }

    // 根据分类id查询商品 -> 对应前台所有商品页面，在选择商品种类之后某类商品的展示
    @GetMapping("/type/{id}")
    public Result type(@PathVariable Integer id) {
        List<Goods> list = goodsService.findByFlId(id);
        return Result.success(list);
    }

    // 查询所有商品 -> 对应后台商品信息管理里面展示所有商品
    @GetMapping // 接收/goods请求
    public Result all() {
        return Result.success(goodsService.findAll());
    }

    // 查询所有热门商品 -> 对应前台首页的热门商品展示
    @GetMapping("/hotgoods")
    public Result hotall() {
        List<Goods> collect = goodsService.findAll().stream().filter(item -> "是".equals(item.getIshot())).collect(Collectors.toList());
        return Result.success(collect);
    }

    // 分页查询所有商品 -> 对应后台商品信息管理里面分页展示所有商品
    @PostMapping("/page")
    public Result page(@RequestBody Goods search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(goodsService.findPage(search, pageNum, pageSize));
    }

    // 分页查询所有热门商品 -> 对应后台热门商品管理的展示
    @PostMapping("/hot/page")
    public Result hotpage(@RequestBody Goods search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(goodsService.findHotPage(search, pageNum, pageSize));
    }

    // 点赞 -> 对应前台商品详情页对商品进行点赞
    @GetMapping("/praise/{goodsId}")
    public Result praise(@PathVariable Integer goodsId) {
        goodsService.praise(goodsId);
        return Result.success();
    }

    // 查看点赞状态 -> 对应前台商品详情页对商品点赞状态进行切换
    @GetMapping("/praise/check/{goodsId}")
    public Result praiseCheck(@PathVariable Integer goodsId) {
        return Result.success(goodsService.praiseCheck(goodsId));
    }

    // 搜索 -> 对应前台搜索栏的搜索按钮
    @GetMapping("/search/{name}")
    public Result search(@PathVariable String name) {
        return Result.success(goodsService.search(name));
    }
}
