package com.example.dao;

import com.example.entity.Notice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

// 不用看。。这个功能应该要删掉
@Repository
public interface NoticeDao extends Mapper<Notice> {

	List<Notice> findBySearch(@Param("search") Notice search);

	@Select("select * from notice order by time desc limit 3")
	List<Notice> findTop3();

}
