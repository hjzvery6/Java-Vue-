package com.example.controller;

import com.example.common.Result;
import com.example.entity.Address;
import com.example.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

// 收获地址相关接口
@RestController // 标记为RESTful控制器，返回的数据通常是JSON格式
@RequestMapping(value = "/address") // 为该控制器的所有接口统一设置路径前缀
public class AddressController {
    // 将服务类注入到控制器中
    @Resource
    private AddressService addressService;

    // 新增地址 -> 对应后台地址管理页面的新增-保存按钮
    @PostMapping // 接收/address请求
    public Result add(@RequestBody Address address) { // 使用@RequestBody注解将请求体中的JSON数据映射为Address对象
        addressService.add(address); // 调用服务类中的add方法
        return Result.success(address);
    }

    // 根据id删除地址 -> 对应后台地址管理页面的删除-确定按钮
    @DeleteMapping("/{id}") // 接收/address/{id}请求
    public Result delete(@PathVariable Integer id) { // 使用@PathVariable注解将URL中的{id}参数映射为id变量
        addressService.delete(id);
        return Result.success();
    }

    // 查询所有地址 -> 对应前端购物车页面加载收货地址
    @GetMapping() // 接收/address请求
    public Result all() {
        return Result.success(addressService.findAll());
    }

    // 分页查询地址 -> 对应后台地址管理页面的分页，以及查询按钮
    @PostMapping("/page") // 接收/address/page请求
    public Result page(@RequestBody Address search,
                       @RequestParam(defaultValue = "1") Integer pageNum, // 有传递就是传递的页码，没有就是1
                       @RequestParam(defaultValue = "5") Integer pageSize)  // 有传递就是传递的每页记录数量，没有就是5
    {
        // 调用服务类中的findPage方法，返回分页查询的结果
        return Result.success(addressService.findPage(search, pageNum, pageSize));
    }
}
