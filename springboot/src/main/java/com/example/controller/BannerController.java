package com.example.controller;

import com.example.common.Result;
import com.example.entity.Banner;
import com.example.service.BannerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

// 轮播图管理接口
@RestController
@RequestMapping(value = "/banner") // 为该控制器的所有接口统一设置路径前缀
public class BannerController {
    // 用注解将服务层注入控制器
    @Resource
    private BannerService bannerService;

    // 新增轮播图 -> 对应后台信息管理/轮播图管理中的新增-保存按钮
    @PostMapping // 接收/banner请求
    public Result add(@RequestBody Banner banner) {
        bannerService.add(banner);
        return Result.success(banner);
    }

    // 根据id删除轮播图 -> 对应后台信息管理/轮播图管理中的删除-确定按钮
    @DeleteMapping("/{id}") // 接收/banner/{id}请求
    public Result delete(@PathVariable Integer id) {
        bannerService.delete(id);
        return Result.success();
    }

    // 更新轮播图 -> 对应后台信息管理/轮播图管理中的编辑-保存按钮
    @PutMapping // 接收/banner请求
    public Result update(@RequestBody Banner banner) {
        bannerService.update(banner);
        return Result.success();
    }

//    // 根据id查询
//    @GetMapping("/{id}") // 接收/banner/{id}请求
//    public Result detail(@PathVariable Integer id) {
//        Banner banner = bannerService.findById(id);
//        return Result.success(banner);
//    }

    // 查询所有轮播图 -> 对应前台首页轮播图的加载
    @GetMapping // 接收/banner请求
    public Result all() {
        return Result.success(bannerService.findAll());
    }

    // 分页查询 -> 对应后台信息管理/轮播图管理中的分页，以及查询按钮
    @PostMapping("/page")
    public Result page(@RequestBody Banner search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(bannerService.findPage(search, pageNum, pageSize));
    }
}
