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
import java.util.stream.Collectors;

// 服务层的实现类，主要负责商品相关的业务逻辑
@Service
public class GoodsService {
    // 使用注解将Dao注入服务类中，用于与数据库交互
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private PraiseDao praiseDao;

    // 增加商品
    public Goods add(Goods goods) {
        goodsDao.insertSelective(goods);
        return goods;
    }

    // 根据id删除商品
    public void delete(Integer id) {
        goodsDao.deleteByPrimaryKey(id);
    }

    // 更新商品
    public void update(Goods goods) {
        goodsDao.updateByPrimaryKeySelective(goods);
    }

    // 根据id查找商品
    public Goods findById(Integer id) {
        return goodsDao.selectByPrimaryKey(id);
    }

    // 查找所有商品
    public List<Goods> findAll() {
        return goodsDao.findBySearch(null);
    }

    // 分页查找商品
    public PageInfo<Goods> findPage(Goods search, Integer pageNum, Integer pageSize) {
        // 获取当前用户
        Account user = getAccount();
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查找所有满足条件的商品
        List<Goods> all = findByCondition(user, search);
        // 封装成PageInfo对象返回
        return PageInfo.of(all);
    }

    // 查找热销商品
    public PageInfo<Goods> findHotPage(Goods search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> hotGoods = goodsDao.findByHot();
        return PageInfo.of(hotGoods);
    }

    // 根据条件查找商品
    public List<Goods> findByCondition(Account user, Goods search) {
		return goodsDao.findBySearch(search);
    }

    // 根据分类id查找商品
    public List<Goods> findByFlId(Integer id) {
        return goodsDao.findByTypeId(id);
    }

    // 对商品点赞
    public void praise(Integer goodsId) {
        // 获取被点赞的商品
        Goods goods = goodsDao.selectByPrimaryKey(goodsId);
        // 获取点赞用户
        Account user = getAccount();
        // 获取点赞用户对该商品的点赞记录
        Praise praise = praiseDao.findByRelIdAndUserIdAndRole(goodsId, user.getId(), user.getRole());
        // 如果没有点赞记录，则创建点赞记录，商品点赞数加1
        if (ObjectUtil.isNull(praise)) {
            Praise tmp = new Praise();
            tmp.setUserId(user.getId());
            tmp.setRole(user.getRole());
            tmp.setRelId(goodsId);
            praiseDao.insertSelective(tmp);
            goods.setPraise(goods.getPraise() + 1); // 点赞商品的点赞数+1
        } else {
            // 如果有点赞记录，则删除点赞记录，商品点赞数减1
            praiseDao.deleteByRelIdAndUserIdAndRole(goodsId, user.getId(), user.getRole());
            goods.setPraise(goods.getPraise() - 1);
        }
        // 更新商品点赞数
        goodsDao.updateByPrimaryKeySelective(goods);
    }

    // 获取当前用户
    private Account getAccount() {
        Account user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        return user;
    }

    // 检查用户是否点赞
    public boolean praiseCheck(Integer goodsId) {
        Account user = getAccount();
        Praise praise = praiseDao.findByRelIdAndUserIdAndRole(goodsId, user.getId(), user.getRole());
        return ObjectUtil.isNotEmpty(praise);
    }

    // 根据商品名称搜索商品
    public List<Goods> search(String name) {
        return goodsDao.findByName(name);
    }
}