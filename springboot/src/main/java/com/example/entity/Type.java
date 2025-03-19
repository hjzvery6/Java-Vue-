package com.example.entity;

import javax.persistence.*;

// 商品种类类
@Table(name = "type") // 指定实体类对应的数据库表
public class Type {
	// 类别id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 类别名称
	@Column(name = "name")
	private String name;

	// 类别描述
	@Column(name = "description")
	private String description;

	// 下面是这些字段的get和set方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
