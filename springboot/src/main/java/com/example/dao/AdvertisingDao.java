package com.example.dao;

import com.example.entity.Advertising;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用于操作advertising表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口AdvertisingDao并继承Mapper<Advertising>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface AdvertisingDao extends Mapper<Advertising> {
	// 通过条件对象查询广告，返回匹配的广告列表
	// 如果传入的是null的时候就是返回所有记录
	List<Advertising> findBySearch(@Param("search") Advertising search);

	// 根据商品id查询相关的广告列表
	@Select("select * from advertising where goodsId = #{id}")
    List<Advertising> findByGoodsId(@Param("id") Integer id);
}
