package com.example.dao;

import com.example.entity.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用来操作goods表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口GoodsDao并继承Mapper<Goods>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface GoodsDao extends Mapper<Goods> {
    // 根据条件对象查找商品，返回匹配的商品列表
	List<Goods> findBySearch(@Param("search") Goods search);

    // 根据类型ID查找商品，返回匹配的商品列表
	@Select("select * from goods where typeId = #{id}")
    List<Goods> findByTypeId(@Param("id") Integer id);

    // 根据商品名称查找商品，返回匹配的商品列表
	@Select("select * from goods where name like concat ('%', #{name}, '%')")
    List<Goods> findByName(@Param("name") String name);

    // 根据是否热门查找商品，返回匹配的商品列表
    // 具体的实现在对应的xml文件中
    List<Goods> findByHot();
}
