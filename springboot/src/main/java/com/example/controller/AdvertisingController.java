package com.example.controller;

import com.example.common.Result;
import com.example.entity.Advertising;
import com.example.service.AdvertisingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

// 广告位管理控制器
@RestController // 标识为控制器
@RequestMapping(value = "/advertising") // 为该控制器的所有接口统一设置路径前缀
public class AdvertisingController {
    // 使用注解将对应的服务层注入控制器
    @Resource
    private AdvertisingService advertisingService;

    // 新增广告 -> 对应后台信息管理/前台广告位管理中的新增-保存按钮
    @PostMapping // 接收/advertising请求
    public Result add(@RequestBody Advertising advertising) {
        advertisingService.add(advertising);
        return Result.success(advertising);
    }

    // 根据id删除广告 -> 对应后台信息管理/前台广告位管理中的删除-确定按钮
    @DeleteMapping("/{id}") // 接收/advertising/{id}请求
    public Result delete(@PathVariable Integer id) {
        advertisingService.delete(id);
        return Result.success();
    }

    // 更新广告 -> 对应后台信息管理/前台广告位管理中的编辑-保存按钮
    @PutMapping // 接收/advertising请求
    public Result update(@RequestBody Advertising advertising) {
        advertisingService.update(advertising);
        return Result.success();
    }

//    // 根据id查询
//    @GetMapping("/{id}") // 接收/advertising/{id}请求
//    public Result detail(@PathVariable Integer id) {
//        Advertising advertising = advertisingService.findById(id);
//        return Result.success(advertising);
//    }

    // 查询所有 -> 对应前台首页中广告的加载
    @GetMapping // 接收/advertising请求
    public Result all() {
        return Result.success(advertisingService.findAll());
    }

    // 分页查询 -> 对应后台信息管理/前台广告位管理中的分页，以及查询按钮
    @PostMapping("/page") // 接收/advertising/page请求
    public Result page(@RequestBody Advertising search, // 通过这个注解将请求体中的JSON数据解析为Advertising对象
                       @RequestParam(defaultValue = "1") Integer pageNum, // 有传递就是传递的页码，没有就是1
                       @RequestParam(defaultValue = "5") Integer pageSize) // 有传递就是传递的每页记录数量，没有就是5
    {
        return Result.success(advertisingService.findPage(search, pageNum, pageSize));
    }
}
