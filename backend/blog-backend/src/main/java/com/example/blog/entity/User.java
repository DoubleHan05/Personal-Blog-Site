package com.example.blog.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

/**
 * 用户实体类，映射数据库中的 users 表
 * 使用 JPA 注解定义实体与表的映射关系
 * 使用 Lombok 注解简化代码
 */
@Entity
@Table(name = "users")
@NoArgsConstructor // 生成无参构造函数
@AllArgsConstructor // 生成全参构造函数
@Builder // 提供构建者模式
public class User {

    /**
     * 用户唯一标识，自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名，不可为空，必须唯一
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 加密后的密码，不可为空
     */
    @Column(nullable = false)
    private String password;

    /**
     * 用户邮箱，不可为空，必须唯一
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 用户昵称，可为空
     */
    private String nickname;

    /**
     * 用户头像 URL，可为空
     */
    private String avatar;

    /**
     * 用户个人简介，可为空
     */
    private String bio;

    /**
     * 用户状态，是否活跃，默认为 true
     * 使用 TINYINT(1) 类型存储布尔值
     */
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isActive = true;

    /**
     * 用户权限，是否为管理员，默认为 false
     * 使用 TINYINT(1) 类型存储布尔值
     */
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isAdmin = false;

    /**
     * 用户创建时间，自动生成，创建后不可修改
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdAt;

    /**
     * 用户最后登录时间，每次更新时自动设置
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginAt;

    // 以下为各字段的 getter 和 setter 方法
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * JPA 生命周期回调方法
     * 在实体创建时自动设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    /**
     * JPA 生命周期回调方法
     * 在实体更新时自动设置最后登录时间
     */
    @PreUpdate
    protected void onUpdate() {
        this.lastLoginAt = new Date();
    }
}