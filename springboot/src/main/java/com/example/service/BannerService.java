package com.example.service;

import cn.hutool.core.util.ObjectUtil;

import com.example.common.ResultCode;
import com.example.common.config.JwtTokenUtils;
import com.example.dao.BannerDao;
import com.example.entity.Account;
import com.example.entity.Banner;
import com.example.exception.CustomException;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

// 服务层实现类，负责管理轮播图相关的业务逻辑
@Service
public class BannerService {
    // 注入BannerDao接口，用于操作数据库
    @Resource
    private BannerDao bannerDao;

    // 新增轮播图
    public Banner add(Banner banner) {
        bannerDao.insertSelective(banner);
        return banner;
    }

    // 根据id删除轮播图
    public void delete(Integer id) {
        bannerDao.deleteByPrimaryKey(id);
    }

    // 更新轮播图
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    // 根据id查找轮播图
    public Banner findById(Integer id) {
        return bannerDao.selectByPrimaryKey(id);
    }

    // 查找所有的轮播图
    public List<Banner> findAll() {
        return bannerDao.findBySearch(null);
    }

    // 分页查询轮播图
    public PageInfo<Banner> findPage(Banner search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户
        Account user = getAccount();
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有满足条件的轮播图
        List<Banner> all = findByCondition(user, search);
        return PageInfo.of(all);
    }

    // 根据条件查找轮播图
    public List<Banner> findByCondition(Account user, Banner search) {
		return bannerDao.findBySearch(search);
    }

    // 获取当前登录用户
    private Account getAccount() {
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        return user;
    }
}