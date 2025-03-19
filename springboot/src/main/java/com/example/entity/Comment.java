package com.example.entity;

import javax.persistence.*;
import java.util.List;

// 评论管理实体类
@Table(name = "comment") // 实体类对应的数据库表
public class Comment {
	// 评论id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 评论内容
	@Column(name = "content")
	private String content;

	// 评论人id
	@Column(name = "userId")
	private Integer userId;

	// 评论人角色
	@Column(name = "role")
	private Integer role;

	// 评论时间
	@Column(name = "time")
	private String time;

	// 父评论的id（因为可能会在评论下面回复评论，就有父评论和子评论）
	@Column(name = "parentId")
	private Integer parentId;

	// 评论相关的商品id
	@Column(name = "relId")
	private Integer relId;

	// 评论人名称
	@Transient // 非持久化
	private String userName;
	// 子评论列表
	@Transient // 非持久化
	private List<Comment> children;
	// 评论人头像
	@Transient // 非持久化
	private String avatar;
	// 关联的商品名称
	@Transient // 非持久化
	private String goodName;

	// 下面是这些字段的get和set方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Comment> getChildren() {
		return children;
	}
	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getRelId() {
		return relId;
	}
	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
}
