package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.Cart;
import com.example.shoppingmallserver.entity.Mileage;
import com.example.shoppingmallserver.entity.User;
import com.example.shoppingmallserver.entity.Wishlist;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 사용자 정보 조회
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    @Override
    public User getAdminUserById(Long user_id) {
        return null;
    }

    // 관리자의 사용자 정보 리스트 조회
    @Override
    public List<User> getUserList(Optional<String> keyword) {
        return null;
    }

    // 관리자의 사용자 정보 수정
    @Override
    public User updateUser(Long user_id, UpdateUserDto updateUserDto) {
        return null;
    }

    // 관리자의 사용자 정보 등록
    @Override
    public User createUser(CreateUserDto userCreateDto) {
        return null;
    }

    // 관리자의 사용자 정보 삭제
    @Override
    public void deleteUser(Long user_id) {

    }
}