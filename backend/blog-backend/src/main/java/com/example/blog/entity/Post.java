// src/main/java/com/example/blog/entity/Post.java
package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data // Lombok注解，自动生成getter/setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private String summary;
    
    private String author;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // JPA生命周期回调，自动设置创建时间和更新时间
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}