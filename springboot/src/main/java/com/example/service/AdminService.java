package com.example.service;

import cn.hutool.core.util.ObjectUtil; // 用于对象判空等工具方法

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

// 基于Spring框架的服务层实现类，主要负责与管理类相关的业务逻辑
// 调用对应的dao类对数据库进行操作，结合用户认证信息进行权限控制
@Service
public class AdminService {
    @Resource // 通过注解注入了AdminDao数据访问层实例
    private AdminDao adminDao;

    // 新增管理员
    public Admin add(Admin admin) {
        // 验证用户名是否已经存在，存在则抛出异常
        Admin info = adminDao.findByUsername(admin.getUsername());
        if (ObjectUtil.isNotEmpty(info)) {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        // 验证是否设置密码，没有默认设为123456
        if (StringUtils.isEmpty(admin.getPassword())) {
            // 默认密码123456
            admin.setPassword("123456");
        }
        // 验证是否设置头像，没有默认设为默认头像
        if (StringUtils.isEmpty(admin.getAvatar())) {
            admin.setAvatar("http://localhost:8080/files/qy-default.png");
        }
        // 将管理员信息插入数据库
        adminDao.insertSelective(admin);
        return admin;
    }

    // 根据主键删除管理员
    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }

    // 更新管理员
    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    // 根据id查询管理员
    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }

    // 查询所有管理员
    public List<Admin> findAll() {
        return adminDao.findBySearch(null);
    }

    // 分页查询管理员
    public PageInfo<Admin> findPage(Admin search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户信息，为空则抛出异常
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isEmpty(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有满足条件的管理员
        List<Admin> all = findByCondition(search);
        // 结果封装成PageInfo对象返回
        return PageInfo.of(all);
    }

    // 根据条件对象查询管理员
    public List<Admin> findByCondition(Admin search) {
		return adminDao.findBySearch(search);
    }

    // 根据用户名查询
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    // 管理员登录方法
    public Admin login(String username, String password) {
        // 根据用户名获取数据库中这个管理员信息
        Admin admin = adminDao.findByUsername(username);
        // 不存在则抛出异常
        if (admin == null) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        // 密码不匹配则抛出异常
        if (!password.equalsIgnoreCase(admin.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return admin;
    }
}
