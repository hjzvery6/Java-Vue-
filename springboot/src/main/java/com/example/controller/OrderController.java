package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.*;
import com.example.service.GoodsService;
import com.example.service.OrderService;
import com.example.service.TypeService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  描述：订单相关接口
*/
@RestController
@RequestMapping(value = "/order") // 为该控制器的所有接口统一设置路径前缀
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private TypeService typeService;

    /**
     * 描述：购买
     */
    @PostMapping("/buy")
    public Result add(@RequestBody OrderDto order) {
        orderService.add(order);
        return Result.success();
    }

    /**
     * 描述：购买一个
     */
    @PostMapping("/buy/one")
    public Result add(@RequestBody Order order) {
        orderService.addOne(order);
        return Result.success();
    }
    /**
     * 描述：根据ID删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        orderService.delete(id);
        return Result.success();
    }

    /**
     * 描述：更新
     */
    @PutMapping
    public Result update(@RequestBody Order order) {

        orderService.update(order);
        return Result.success();
    }

    /**
     * 描述：根据ID查询
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        return Result.success(order);
    }

    /**
     * 描述：查询所有
     */
    @GetMapping
    public Result all() {
        return Result.success(orderService.findAll());
    }

    /**
     * 描述：分页查询
     */
    @PostMapping("/page")
    public Result page(@RequestBody Order search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(orderService.findPage(search, pageNum, pageSize));
    }

    /**
     * 描述：统计订单柱状图
     */
    @GetMapping("/echarts/bar")
    public Result bar() {
        List<Order> list = orderService.findBarAll();

        Map<String, Integer> map = new HashMap<>();
        for (Order order : list) {
            Integer goodsId = order.getGoodsId();
            Integer num = order.getNum();
            Goods goods = goodsService.findById(goodsId);
            if (ObjectUtil.isNotEmpty(goods)){
                Type type = typeService.findById(goods.getTypeId());
                if (ObjectUtil.isNotEmpty(type)){
                    Integer value = map.get(type.getName());
                    if (ObjectUtil.isEmpty(value)) {
                        map.put(type.getName(), num);
                    } else {
                        map.put(type.getName(), value + num);
                    }
                }
            }
        }
        List<String> xAxis = new ArrayList<>();
        List<Integer> yAxis = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(map)) {
            for (String key : map.keySet()) {
                xAxis.add(key);
                yAxis.add(map.get(key));
            }
        }

        // 最后返回给前端的数据结构
        Map<String, Object> mapList = new HashMap<>();
        mapList.put("xAxis", xAxis);
        mapList.put("yAxis", yAxis);
        return Result.success(mapList);
    }

    /**
     * 描述：按照商品id销量饼图
     */
    @GetMapping("/echarts/bie")
    public Result bie() {
        List<Order> list = orderService.findBieAll();
        // 最后返回给前端的数据结构
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<Integer, Double> map = new HashMap<>();
        for (Order order : list) {
            Integer goodsId = order.getGoodsId();
            Integer num = order.getNum();
            Goods goods = goodsService.findById(goodsId);
            if (ObjectUtil.isNotEmpty(goods)){
                double price = num * goods.getPrice();
                Double value = map.get(goodsId);
                if (ObjectUtil.isEmpty(value)) {
                    map.put(goodsId, price);
                } else {
                    map.put(goodsId, value + price);
                }
            }
        }
        for (Integer key : map.keySet()) {
            Map<String, Object> tmpMap = new HashMap<>();
            Goods goods = goodsService.findById(key);
            if (ObjectUtil.isNotEmpty(goods)){
                tmpMap.put("name", goods.getName());
            }
            tmpMap.put("value", map.get(key));
            mapList.add(tmpMap);
        }
        return Result.success(mapList);
    }

}
