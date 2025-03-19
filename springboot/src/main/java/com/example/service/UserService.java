package com.example.service;

import cn.hutool.core.util.ObjectUtil;

import com.example.common.config.JwtTokenUtils;
import com.example.common.ResultCode;
import com.example.dao.*;
import com.example.entity.*;
import com.example.exception.CustomException;

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.List;

// 基于Spring框架的服务层实现类，主要负责与用户相关的业务逻辑
// 调用对应的dao类对数据库进行操作，结合用户认证信息进行权限控制
@Service
public class UserService {
    @Resource // 通过注解注入了UserDao数据访问层实例
    private UserDao userDao;

    // 新增用户
    public User add(User user) {
        // 验证用户名是否已经存在，存在则抛出异常
        User info = userDao.findByUsername(user.getUsername());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        // 验证是否设置密码，没有则默认设为123456
        if (StringUtils.isEmpty(user.getPassword())) {
            // 默认密码123456
            user.setPassword("123456");
        }
        // 验证是否设置头像，没有则默认设为默认头像
        if (StringUtils.isEmpty(user.getAvatar())) {
            user.setAvatar("http://localhost:8080/files/qy-default.png");
        }
        // 将用户信息插入数据库
        userDao.insertSelective(user);
        return user;
    }

    // 根据用户id删除用户
    public void delete(Integer id) {
        userDao.deleteByPrimaryKey(id);
    }

    // 更新用户信息
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    // 根据id查找用户
    public User findById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    // 查询所有用户记录
    public List<User> findAll() {
        return userDao.findBySearch(null);
    }

    // 分页查询用户
    public PageInfo<User> findPage(User search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户信息，为空则抛出异常
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isEmpty(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有满足条件的用户
        List<User> all = findByCondition(search);
        // 结果封装成PageInfo对象返回
        return PageInfo.of(all);
    }

    // 根据条件对象查询用户
    public List<User> findByCondition(User search) {
		return userDao.findBySearch(search);
    }

    // 根据用户名查找用户
    public User findByUserName(String username) {
        return userDao.findByUsername(username);
    }

    // 用户登录
    public User login(String username, String password) {
        // 根据用户名获取用户信息
        User user = userDao.findByUsername(username);
        // 如果用户不存在则抛出异常
        if (user == null) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        // 如果密码不匹配则抛出异常
        if (!password.equalsIgnoreCase(user.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return user;
    }
}
