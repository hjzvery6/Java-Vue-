package com.example.entity;

import javax.persistence.*;
import java.util.List;

// 订单类
@Table(name = "orderInfo") // 实体类对应的数据库表
public class Order {
	// 记录的id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	// 订单id
	@Column(name = "orderId")
	private String orderId;

	// 用户id
	@Column(name = "userId")
	private Integer userId;

	// 商品id
	@Column(name = "goodsId")
	private Integer goodsId;

	// 地址id
	@Column(name = "addressId")
	private Integer addressId;

	// 购买数量
	@Column(name = "num")
	private Integer num;

	// 下单时间
	@Column(name = "time")
	private String time;

	// 订单总价格
	@Column(name = "price")
	private Double price;

	// 订单状态
	@Column(name = "status")
	private String status;

	// 订单用户名
	@Transient // 非持久化
	private String userName;
	// 商品信息
	@Transient // 非持久化
	private Goods goods;
	// 商品名称
	@Transient // 非持久化
	private String name;
	// 地址
	@Transient // 非持久化
	private String address;
	// 电话号码
	@Transient // 非持久化
	private String phone;

	// 下面是这些字段的get方法和set方法
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
