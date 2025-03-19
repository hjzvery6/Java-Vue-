package com.example.entity;

import javax.persistence.*;

// 轮播图展示管理类
@Table(name = "banner") // 实体类对应的数据库表
public class Banner {
	// 轮播图id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 轮播图图片
	@Column(name = "img")
	private String img;

	// 轮播图对应商品id
	@Column(name = "goodsId")
	private Integer goodsId;

	// 商品名称
	@Transient // 不会被持久化到数据库中
	private String goodName;

	// 下面是这些字段的get和set方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
}
