package com.example.entity;

import javax.persistence.*;

// 广告位展示管理类
@Table(name = "advertising") // 实体类对应的数据库表
public class Advertising {
    // 广告位id
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 广告位图片
    @Column(name = "img")
    private String img;

    // 对应的商品id
    @Column(name = "goodsId")
    private Integer goodsId;

    // 广告位主标题
    @Column(name = "title")
    private String title;

    // 广告位副标题
    @Column(name = "introducing")
    private String introducing;

    // 商品介绍
    @Column(name = "description")
    private String description;

    // 商品名称
    @Transient // 不被持久化到数据库
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

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroducing() {
        return introducing;
    }
    public void setIntroducing(String introducing) {
        this.introducing = introducing;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
