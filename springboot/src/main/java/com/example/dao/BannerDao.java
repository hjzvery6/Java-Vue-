package com.example.dao;

import com.example.entity.Banner;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用于操作banner表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口BannerDao并继承Mapper<Banner>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface BannerDao extends Mapper<Banner> {
	// 通过条件对象查询轮播图，返回匹配的轮播图列表
	List<Banner> findBySearch(@Param("search") Banner search);

	// 根据商品id查询相关的轮播图列表
	@Select("select * from banner where goodsId = #{id}")
    List<Banner> findByGoodsId(@Param("id") Integer id);

}
