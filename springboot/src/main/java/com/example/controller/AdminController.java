package com.example.controller;

import com.example.common.Result;
import com.example.entity.*;
import com.example.service.*;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

// 控制器类，用于提供管理员相关的api接口
@RestController // 标识这是一个RESTful控制器，返回的数据通常是JSON格式
@RequestMapping(value = "/admin") // 为该控制器的所有接口统一设置路径前缀
public class AdminController {
    // 使用注解将服务类注入控制器中，用于调用服务层方法
    @Resource
    private AdminService adminService;

    // 新增管理员 -> 对应后台用户管理/管理员中的新增-保存按钮
    @PostMapping // 接收/admin请求
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success(admin);
    }

    // 根据id删除 -> 对应后台用户管理/管理员中的删除-确定按钮
    @DeleteMapping("/{id}") // 接收/admin/{id}请求
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return Result.success();
    }

    // 更新管理员 -> 对应后台用户管理/管理员中的编辑-保存按钮
    @PutMapping // 接收/admin请求
    public Result update(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }

//    // 根据id查询 -> 对应后台用户管理/管理员中的查询功能，当有搜索内容的时候就会查询一个管理员
//    @GetMapping("/{id}") // 接收/admin/{id}请求
//    public Result detail(@PathVariable Integer id) {
//        Admin admin = adminService.findById(id);
//        return Result.success(admin);
//    }

//    // 查询所有 -> 好像没用到
//    @GetMapping // 接收/admin请求
//    public Result all() {
//        return Result.success(adminService.findAll());
//    }

    // 分页查询 -> 对应后台用户管理/管理员页面的分页，以及查询按钮
    @PostMapping("/page") // 接收/admin/page请求
    public Result page(@RequestBody Admin search, // 通过这个注解将请求体中的JSON数据解析为Admin对象
                       @RequestParam(defaultValue = "1") Integer pageNum, // 有传递就是传递的页码，没有就是1
                       @RequestParam(defaultValue = "5") Integer pageSize) // 有传递就是传递的每页记录数量，没有就是5
    {
        // 调用服务类中的findPage方法，返回分页查询的结果
        return Result.success(adminService.findPage(search, pageNum, pageSize));
    }
}
