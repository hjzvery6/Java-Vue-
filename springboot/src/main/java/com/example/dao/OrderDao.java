package com.example.dao;

import com.example.entity.Order;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

// DAO接口，用来操作orderinfo表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口OrderDao并继承Mapper<Order>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface OrderDao extends Mapper<Order> {
	// 根据条件对象查找订单，返回匹配的订单列表
	List<Order> findBySearch(@Param("search") Order search);

	// 根据订单对应的userid查找订单，返回匹配的订单列表
	@Select("select * from orderInfo where userId = #{id}")
	List<Order> findByUserId(@Param("id") Integer id);

}
