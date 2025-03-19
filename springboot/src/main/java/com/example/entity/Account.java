package com.example.entity;

import javax.persistence.*;

// 角色用户父类，使用了JPA注解，表明它是一个与数据库表映射的实体类
public class Account {
    @Id // 标识该字段为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 用户名
    @Column(name = "username")
    private String username;

    // 密码
    @Column(name = "password")
    private String password;

    // 角色标识
    @Column(name = "role")
    private Integer role;

    // 头像
    @Column(name = "avatar")
    private String avatar;

    // 新密码
    @Transient // 这个字段不会被持久化到数据库中
    private String npw;

    // 下面是对这些变量的get和set方法
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }

    public String getNpw() {
        return npw;
    }
    public void setNpw(String nmm) {
        this.npw = nmm;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
