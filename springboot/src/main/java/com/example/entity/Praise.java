package com.example.entity;

import javax.persistence.*;

// 点赞类
@Table(name = "praise") // 指定实体类对应的数据库表
public class Praise {
	// 记录id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 点赞人id
	@Column(name = "userId")
	private Integer userId;

	// 点赞人角色
	@Column(name = "role")
	private Integer role;

	// 关联id
	@Column(name = "relId")
	private Integer relId;

	// 点赞人用户名
	@Transient // 非持久化
	private String userName;
	// 点赞商品图片
	@Transient // 非持久化
	private String avatar;

	// 下面是这些字段的get和set方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
}
