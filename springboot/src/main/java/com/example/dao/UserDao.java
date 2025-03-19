package com.example.dao;

import com.example.entity.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用来操作user表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口UserDao并继承Mapper<User>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface UserDao extends Mapper<User> {
	// 根据用户名查询用户，返回匹配的用户
	@Select("select * from user where `username` = #{name}")
	User findByUsername(@Param("name") String name);

	// 根据条件对象查询用户，返回匹配的用户列表
	List<User> findBySearch(@Param("search") User search);

}
