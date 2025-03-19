package com.example.dao;

import com.example.entity.Praise;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

// DAO接口，用来操作praise表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口PraiseDao并继承Mapper<Praise>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface PraiseDao extends Mapper<Praise> {
	// 根据relId、userId和role查询点赞记录，返回匹配的点赞对象
	@Select("select * from praise where relId = #{relId} and userId = #{userId} and role = #{role}")
	Praise findByRelIdAndUserIdAndRole(@Param("relId") Integer goodsId, @Param("userId") Integer userId, @Param("role") Integer role);

	// 根据relId、userId和role删除点赞记录
	@Delete("delete from praise where relId = #{relId} and userId = #{userId} and role = #{role}")
	void deleteByRelIdAndUserIdAndRole(@Param("relId") Integer goodsId, @Param("userId") Integer userId, @Param("role") Integer role);
}
