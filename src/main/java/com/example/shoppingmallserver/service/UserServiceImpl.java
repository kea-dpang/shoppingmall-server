package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.repository.UserDetailRepository;
import com.example.shoppingmallserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDetailRepository userDetailRepository;

    // 사용자 정보 조회
    @Override
    public UserDetail getUserById(Long userId) {
        return userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    @Override
    public UserDetail getAdminUserById(Long userId) {
        return userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    // 관리자의 사용자 정보 리스트 조회
    @Override
    public List<UserDetail> getUserList(String keyword) {
        if(keyword != null) {
            return userDetailRepository.findByNameContaining(keyword);
        }
        else {
            return userDetailRepository.findAll();
        }
    }

    // 관리자의 사용자 정보 삭제
    @Override
    public void deleteUser(Long userId) {

    }
}