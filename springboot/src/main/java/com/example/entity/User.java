package com.example.entity;

import javax.persistence.*;

// 普通用户类
@Table(name = "user") // 指定实体类对应的数据库表
public class User extends Account { // 继承Account类，拥有父类的所有字段和方法
	// 用户id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 昵称
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

	// 年龄
	@Column(name = "age")
	private String age;

	// 账户余额
	@Column(name = "account")
	private Double account;

	// 真实姓名
	@Column(name = "realname")
	private String realname;

	// 下面是这些字段的get和set方法
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

	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}

	public Double getAccount() {
		return account;
	}
	public void setAccount(Double account) {
		this.account = account;
	}

	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
}
