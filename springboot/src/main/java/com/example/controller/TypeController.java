package com.example.controller;

import com.example.common.Result;
import com.example.entity.*;
import com.example.service.*;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

// 商品分类接口
@RestController
@RequestMapping(value = "/type") // 为该控制器的所有接口统一设置路径前缀
public class TypeController {
    // 使用注解将服务类注入控制器
    @Resource
    private TypeService typeService;

    // 新增商品种类 -> 对应后台商品分类管理新增-保存按钮
    @PostMapping // 接收/type请求
    public Result add(@RequestBody Type type) {
        typeService.add(type);
        return Result.success(type);
    }

    // 删除商品种类 -> 对应后台商品分类管理删除-确定按钮
    @DeleteMapping("/{id}") // 接收/type/{id}请求
    public Result delete(@PathVariable Integer id) {
        typeService.delete(id);
        return Result.success();
    }

    // 更新商品种类 -> 对应后台商品分类管理修改-保存按钮
    @PutMapping // 接收/type请求
    public Result update(@RequestBody Type type) {
        typeService.update(type);
        return Result.success();
    }

//    // 根据id查询商品种类
//    @GetMapping("/{id}") // 接收/type/{id}请求
//    public Result detail(@PathVariable Integer id) {
//        Type type = typeService.findById(id);
//        return Result.success(type);
//    }

    // 查询所有商品种类 -> 对应前台所有商品页面在加载的时候获取了所有商品种类
    @GetMapping // 接收/type请求
    public Result all() {
        return Result.success(typeService.findAll());
    }

    // 分页查询商品种类 -> 对应后台商品分类管理分页，以及查询按钮
    @PostMapping("/page")
    public Result page(@RequestBody Type search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(typeService.findPage(search, pageNum, pageSize));
    }
}
