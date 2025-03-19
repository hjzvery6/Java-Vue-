package com.example.controller;

import com.example.common.Result;
import com.example.entity.Comment;
import com.example.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

// 评论管理接口
@RestController
@RequestMapping(value = "/comment") // 为该控制器的所有接口统一设置路径前缀
public class CommentController {
    // 使用注解将服务类注入控制器
    @Resource
    private CommentService commentService;

    // 新增评论 -> 对应前台商品详情页下进行评论时的回复按钮
    @PostMapping // 接收/comment请求
    public Result add(@RequestBody Comment comment) {
        commentService.add(comment);
        return Result.success(comment);
    }

    // 根据id删除评论 -> 对应后台信息管理/评论管理中的删除-确定按钮
    @DeleteMapping("/{id}") // 接收/comment/{id}请求
    public Result delete(@PathVariable Integer id) {
        commentService.delete(id);
        return Result.success();
    }

    // 查询某商品的所有评论 -> 对应前台商品详情页的评论展示
    @GetMapping("/{goodsId}") // 接收/comment/{goodsId}请求
    public Result all(@PathVariable Integer goodsId) {
        return Result.success(commentService.findAll(goodsId));
    }

    // 分页查询 -> 对应后台信息管理/评论管理中的分页，以及查询按钮
    @PostMapping("/page") // 接收/comment/page请求
    public Result page(@RequestBody Comment search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(commentService.findPage(search, pageNum, pageSize));
    }
}
