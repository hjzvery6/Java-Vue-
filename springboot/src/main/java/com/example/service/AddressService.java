package com.example.service;

import cn.hutool.core.util.ObjectUtil;

import com.example.common.ResultCode;
import com.example.common.config.JwtTokenUtils;
import com.example.dao.AddressDao;
import com.example.entity.Account;
import com.example.entity.Address;
import com.example.exception.CustomException;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

// 服务层实现类，负责与地址相关的业务逻辑
@Service // 标识该类为服务类
public class AddressService {
    @Resource // 通过该注解注入了AddressDao层实例
    private AddressDao addressDao;

    // 新增一条地址记录
    public Address add(Address address) {
        addressDao.insertSelective(address);
        return address;
    }

    // 根据id删除地址记录
    public void delete(Integer id) {
        addressDao.deleteByPrimaryKey(id);
    }

    // 查询所有地址
    public List<Address> findAll() {
        // 获取当前登录用户的信息
        Account currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CustomException("-1", "登录失效，请重新登录");
        }
        // 通过当前登录用户的id和角色查询地址
        return addressDao.findByUserIdAndRole(currentUser.getId(), currentUser.getRole());
    }

    // 分页查询地址
    public PageInfo<Address> findPage(Address search, Integer pageNum, Integer pageSize) {
        // 获取当前登录用户的信息
        Account currentUser = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(currentUser)) {
            throw new CustomException(ResultCode.USER_NOT_LOGIN);
        }
        // 开启分页，开启后能根据指定的页码和每页大小从数据库中只查询当前页的数据
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有满足条件的地址
        List<Address> all = findByCondition(search, currentUser);
        // 结果封装成PageInfo对象返回，对象中最重要的就是当前页的数据
        return PageInfo.of(all);
    }

    // 根据条件查询地址
    public List<Address> findByCondition(Address search, Account currentUser) {
        // 如果用户角色为管理员，调用addressDao.findBySearch()方法，忽略用户ID和角色限制
        if (1 == currentUser.getRole()) {
            return addressDao.findBySearch(search, null, null);
        } else {
            // 否则就是普通用户，调用addressDao.findBySearch()方法，只查询当前用户的地址
            return addressDao.findBySearch(search, currentUser.getId(), currentUser.getRole());
        }
    }
}