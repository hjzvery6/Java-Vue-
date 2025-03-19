package com.example.entity;

import javax.persistence.*;

// 购物车类
@Table(name = "cart") // 实体类对应的数据库表
public class Cart {
	// 购物车id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 商品id
	@Column(name = "goodsId")
	private Integer goodsId;

	// 加入购物车的用户id
	@Column(name = "userId")
	private Integer userId;

	// 加入购物车的商品数量
	@Column(name = "num")
	private Integer num;

	// 商品名称
	@Transient // 不会被持久化到数据库中
	private String goodsName;
	// 商品图片
	@Transient // 不会被持久化到数据库中
	private String img;
	// 商品价格
	@Transient // 不会被持久化到数据库中
	private Double price;

	// 下面是这些字段的get和set方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
