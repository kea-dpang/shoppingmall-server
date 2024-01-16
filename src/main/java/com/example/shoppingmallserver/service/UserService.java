package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.entity.User;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUserById(Long id); // 사용자 정보 조회
}
