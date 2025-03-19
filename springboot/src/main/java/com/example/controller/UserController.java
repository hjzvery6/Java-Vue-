package com.example.controller;

import com.example.common.Result;
import com.example.entity.*;
import com.example.service.*;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

// 管理员相关接口
@RestController
@RequestMapping(value = "/user") // 为该控制器的所有接口统一设置路径前缀
public class UserController {
    // 使用注解将服务类注入控制器中
    @Resource
    private UserService userService;

    // 新增用户 -> 对应管理员后台用户管理/普通用户中的新增-保存按钮
    @PostMapping // 接收/user请求
    public Result add(@RequestBody User user) {
        userService.add(user);
        return Result.success(user);
    }

    // 根据id删除 -> 对应管理员后台用户管理/普通用户中的删除-确定按钮
    @DeleteMapping("/{id}") // 接收/user/{id}请求
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }

    // 更新用户 -> 对应管理员后台用户管理/普通用户中的修改-保存按钮
    @PutMapping // 接收/user请求
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

//    // 根据id查询 -> 对应管理员后台用户管理/普通用户中的修改-按钮
//    @GetMapping("/{id}")
//    public Result detail(@PathVariable Integer id) {
//        User user = userService.findById(id);
//        return Result.success(user);
//    }
//
//    // 查询所有
//    @GetMapping
//    public Result all() {
//        return Result.success(userService.findAll());
//    }

    // 分页查询 -> 对应管理员后台用户管理/普通用户中的分页，以及查询按钮
    @PostMapping("/page")
    public Result page(@RequestBody User search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(userService.findPage(search, pageNum, pageSize));
    }
}
