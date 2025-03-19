package com.example.dao;

import com.example.entity.Comment;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

// DAO接口，用来操作comment表
@Repository // 将接口标记为Spring的数据访问层组件
// 定义一个公共接口CommentDao并继承Mapper<Comment>接口，意味着该接口可以使用Mapper提供的CRUD方法
public interface CommentDao extends Mapper<Comment> {
	// 通过条件对象、userId和role三个参数来查询评论，返回匹配的评论列表
	List<Comment> findBySearch(@Param("search") Comment search, @Param("userId") Integer userId, @Param("role") Integer role);

	// 通过relId和parentId两个参数来查询评论，返回匹配的评论列表
	@Select("select * from comment where relId = #{relId} and parentId = #{parentId}")
	List<Comment> findByGoodsIdAndParentId(@Param("relId") Integer goodsId, @Param("parentId") Integer parentId);
}
