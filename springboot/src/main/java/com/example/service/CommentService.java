package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.example.common.ResultCode;
import com.example.common.config.JwtTokenUtils;
import com.example.dao.CommentDao;
import com.example.dao.AdminDao;
import com.example.dao.UserDao;
import com.example.entity.*;
import com.example.exception.CustomException;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

// 服务层实现类，主要负责评论相关的业务逻辑，注意评论中有父评论和子评论
@Service
public class CommentService {
    // 使用注解将CommentDao、AdminDao和UserDao注入到服务类中，用于与数据库交互
    @Resource
    private CommentDao commentDao;
    @Resource
    private AdminDao adminDao;
    @Resource
    private UserDao userDao;

    // 添加评论
    public Comment add(Comment comment) {
        // 设置评论的时间
        comment.setTime(DateUtil.now());
        // 将评论插入数据库
        commentDao.insertSelective(comment);
        return comment;
    }

    // 根据id删除评论
    public void delete(Integer id) {
        commentDao.deleteByPrimaryKey(id);
    }

    // 查找某个商品的所有评论
    public List<Comment> findAll(Integer goodsId) {
        // 获取当前登录用户
        Account currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CustomException("-1", "登录失效，请重新登录");
        }
        // 根据商品id和父评论id查询评论列表（0表示父评论）
        List<Comment> list = commentDao.findByGoodsIdAndParentId(goodsId, 0);
        // 遍历父评论列表
        for (Comment comment : list) {
            // 填充评论信息中的用户名和头像
            setData(comment);
            // 根据商品id和子评论id查询子评论列表
            List<Comment> children = commentDao.findByGoodsIdAndParentId(goodsId, comment.getId());
            // 如果子评论列表不为空，则遍历子评论列表，填充子评论信息中的用户名和头像
            if (CollectionUtil.isNotEmpty(children)) {
                for (Comment child : children) {
                    setData(child);
                }
                // 递归处理子评论列表，填充子评论信息中的用户名和头像
                comment.setChildren(children);
            }
        }
        return list;
    }

    // 根据评论的用户id和角色，填充评论信息中的用户名和头像
    private void setData(Comment comment) {
        // 如果评论是管理员发出的，则填充管理员信息
        if (1 == comment.getRole()) {
            Admin admin = adminDao.selectByPrimaryKey(comment.getUserId());
            comment.setUserName(admin.getUsername());
            comment.setAvatar(admin.getAvatar());
        }
        // 如果评论是用户发出的，则填充用户信息
        if (2 == comment.getRole()) {
            User user = userDao.selectByPrimaryKey(comment.getUserId());
            comment.setUserName(user.getUsername());
            comment.setAvatar(user.getAvatar());
        }
    }

    // 分页查询
    public PageInfo<Comment> findPage(Comment search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户
        Account currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有满足条件的评论
        List<Comment> all = findByCondition(search, currentUser);
        // 遍历评论列表，填充用户名和头像
        for (Comment comment : all) {
            Integer role = comment.getRole();
            if (1 == role) {
                Admin admin = adminDao.selectByPrimaryKey(comment.getUserId());
                comment.setUserName(admin.getUsername());
            } else {
                User user = userDao.selectByPrimaryKey(comment.getUserId());
                comment.setUserName(user.getUsername());
            }
        }
        // 返回分页信息
        return PageInfo.of(all);
    }

    // 根据条件查找评论
    public List<Comment> findByCondition(Comment search, Account currentUser) {
        // 如果用户角色为管理员，调用commentDao.findBySearch()方法，忽略用户ID和角色限制
        if (1 == currentUser.getRole()) {
            return commentDao.findBySearch(search, null, null);
        } else {
            // 否则就是普通用户，调用commentDao.findBySearch()方法，只查询当前用户的评论
            return commentDao.findBySearch(search, currentUser.getId(), currentUser.getRole());
        }
    }
}