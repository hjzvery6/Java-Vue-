package com.example.dao;

import com.example.entity.Cart;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用于操作cart表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口CartDao并继承Mapper<Cart>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface CartDao extends Mapper<Cart> {
	// 通过条件对象动态查询购物车，返回匹配的购物车列表
	List<Cart> findBySearch(@Param("search") Cart search);

	// 通过用户id查询购物车，返回匹配的购物车列表
	@Select("select * from cart where userId = #{id}")
	List<Cart> findByUserId(@Param("id") Integer id);
}
