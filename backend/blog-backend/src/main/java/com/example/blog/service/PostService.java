// src/main/java/com/example/blog/service/PostService.java
package com.example.blog.service;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    // 获取所有文章
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    // 根据ID获取文章
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    
    // 创建文章
    public Post createPost(Post post) {
        return postRepository.save(post);
    }
    
    // 更新文章
    public Post updatePost(Long id, Post postDetails) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postDetails.getTitle());
                    post.setContent(postDetails.getContent());
                    post.setSummary(postDetails.getSummary());
                    post.setAuthor(postDetails.getAuthor());
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }
    
    // 删除文章
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}