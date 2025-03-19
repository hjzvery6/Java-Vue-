package com.example.service;

import cn.hutool.core.util.ObjectUtil;

import com.example.common.ResultCode;
import com.example.common.config.JwtTokenUtils;
import com.example.dao.AdvertisingDao;
import com.example.dao.PraiseDao;
import com.example.entity.Account;
import com.example.entity.Advertising;
import com.example.entity.Praise;
import com.example.exception.CustomException;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

// 服务层实现类，负责与广告相关的业务逻辑
@Service
public class AdvertisingService {
    // 注入AdvertisingDao对象，便于在服务层调用dao层的方法操作数据库
    @Resource
    private AdvertisingDao advertisingDao;

    // 新增广告
    public Advertising add(Advertising advertising) {
        advertisingDao.insertSelective(advertising);
        return advertising;
    }

    // 根据id删除广告
    public void delete(Integer id) {
        advertisingDao.deleteByPrimaryKey(id);
    }

    // 更新广告
    public void update(Advertising advertising) {
        advertisingDao.updateByPrimaryKeySelective(advertising);
    }

    // 根据id查找广告
    public Advertising findById(Integer id) {
        return advertisingDao.selectByPrimaryKey(id);
    }

    // 查找所有广告
    public List<Advertising> findAll() {
        return advertisingDao.findBySearch(null); // 传入null表示无条件查询
    }

    // 分页查找广告
    public PageInfo<Advertising> findPage(Advertising search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户
        Account user = getAccount();
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有满足条件的广告
        List<Advertising> all = findByCondition(user, search);
        // 结果封装成PageInfo对象返回
        return PageInfo.of(all);
    }

    // 按条件查询广告
    public List<Advertising> findByCondition(Account user, Advertising search) {
		return advertisingDao.findBySearch(search);
    }

    // 获取当前用户登录
    private Account getAccount() {
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        return user;
    }
}