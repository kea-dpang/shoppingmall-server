package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.UserReadDto;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserReadDto getUserById(Long id); // 사용자 정보 조회
}
