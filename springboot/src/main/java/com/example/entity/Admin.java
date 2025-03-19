package com.example.entity;

import javax.persistence.*;

// 管理员类，继承自Account类，拥有父类的所有字段和方法
@Table(name = "admin") // 指定实体类对应的数据库表
public class Admin extends Account { // 继承Account类
	// 管理员id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 用户名
	@Column(name = "username")
	private String username;

	// 密码
	@Column(name = "password")
	private String password;

	// 头像
	@Column(name = "avatar")
	private String avatar;

	// 手机号
	@Column(name = "phone")
	private String phone;

	// 性别
	@Column(name = "sex")
	private String sex;

	// 下面是字段的get和set方法
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
