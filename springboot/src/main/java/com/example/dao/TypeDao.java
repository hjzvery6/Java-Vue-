package com.example.dao;

import com.example.entity.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用来操作type表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口TypeDao并继承Mapper<Type>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface TypeDao extends Mapper<Type> {
	// 根据条件对象查询商品类别，返回匹配的类别列表
	List<Type> findBySearch(@Param("search") Type search);

}
