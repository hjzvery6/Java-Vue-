package com.example.entity;

import javax.persistence.*; // JPA相关的注解和接口

// 收货地址类，使用了JPA注解，表明它是一个与数据库表映射的实体类
@Table(name = "address") // 指定实体类对应的数据库表名
public class Address {
	// 地址id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 购买用户id
	@Column(name = "userId")
	private Integer userId;

	// 用户角色
	@Column(name = "role")
	private Integer role;

	// 收货人
	@Column(name = "username")
	private String username;

	// 联系地址
	@Column(name = "address")
	private String address;

	// 联系电话
	@Column(name = "phone")
	private String phone;

	// 购买用户
	@Transient // 表明该字段不会被持久化到数据库中
	private String user;

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

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
