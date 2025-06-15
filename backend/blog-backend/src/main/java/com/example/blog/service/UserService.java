package com.example.blog.service;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 需要配置Bean

    // 创建用户
    public User createUser(User user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // 获取所有用户
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 根据ID获取用户
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 根据用户名获取用户
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 更新用户信息
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
                    if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
                    if (updatedUser.getPassword() != null) 
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    if (updatedUser.getNickname() != null) user.setNickname(updatedUser.getNickname());
                    if (updatedUser.getAvatar() != null) user.setAvatar(updatedUser.getAvatar());
                    if (updatedUser.getBio() != null) user.setBio(updatedUser.getBio());
                    if (updatedUser.getIsActive() != null) user.setIsActive(updatedUser.getIsActive());
                    if (updatedUser.getIsAdmin() != null) user.setIsAdmin(updatedUser.getIsAdmin());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // 删除用户
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 检查用户名是否存在
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // 检查邮箱是否存在
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}