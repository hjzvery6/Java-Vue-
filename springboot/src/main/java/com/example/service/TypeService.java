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
import javax.annotation.Resource;
import java.util.List;

// 服务层的业务类，主要负责商品分类相关的业务逻辑
@Service
public class TypeService {
    // 注解将Dao注入服务层
    @Resource
    private TypeDao typeDao;

    // 新增种类
    public Type add(Type type) {
        typeDao.insertSelective(type);
        return type;
    }

    // 根据id删除种类
    public void delete(Integer id) {
        typeDao.deleteByPrimaryKey(id);
    }

    // 更新商品种类
    public void update(Type type) {
        typeDao.updateByPrimaryKeySelective(type);
    }

    // 根据id查询种类
    public Type findById(Integer id) {
        return typeDao.selectByPrimaryKey(id);
    }

    // 查询所有商品种类
    public List<Type> findAll() {
        return typeDao.findBySearch(null);
    }

    // 分页查询商品种类
    public PageInfo<Type> findPage(Type search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查找所有满足条件的商品
        List<Type> all = findByCondition(user, search);
        // 封装成PageInfo对象返回
        return PageInfo.of(all);
    }

    // 根据条件查询商品种类
    public List<Type> findByCondition(Account user, Type search) {
		return typeDao.findBySearch(search);
    }
}