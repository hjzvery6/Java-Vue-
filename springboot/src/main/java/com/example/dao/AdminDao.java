package com.example.dao;

import com.example.entity.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用于操作admin表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口AdminDao并继承Mapper<Admin>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface AdminDao extends Mapper<Admin> {
	// 根据用户名查询管理员信息，返回符合的Admin对象
	@Select("select * from admin where `username` = #{name}")
	Admin findByUsername(@Param("name") String name);

	// 根据条件对象查询管理员信息，返回符合条件的Admin列表
	List<Admin> findBySearch(@Param("search") Admin search);
}
