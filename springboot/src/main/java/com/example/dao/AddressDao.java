package com.example.dao;

import com.example.entity.Address;
import com.example.entity.Comment;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用于操作address表
@Repository // 将该接口标记为Spring的数据访问层组件
// 定义一个公共接口AddressDao并继承Mapper<Address>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface AddressDao extends Mapper<Address> {
	// 根据条件查询地址信息，需要传递一个Address对象、还有userId和role三个参数才可以使用
	// 返回一个List集合，其中每个元素都是Address对象
	List<Address> findBySearch(@Param("search") Address search, @Param("userId") Integer userId, @Param("role") Integer role);

	// Select注解直接定义了SQL语句，根据userId和role两个参数进行查询
	// 返回一个List集合，其中每个元素都是Address对象
	@Select("select * from address where userId = #{userId} and role = #{role}")
	List<Address> findByUserIdAndRole(@Param("userId") Integer userId, @Param("role") Integer role);

}
