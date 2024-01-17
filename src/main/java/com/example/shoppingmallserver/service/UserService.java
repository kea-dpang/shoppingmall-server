package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.Cart;
import com.example.shoppingmallserver.entity.Mileage;
import com.example.shoppingmallserver.entity.User;

import com.example.shoppingmallserver.entity.Wishlist;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    // 사용자 정보 조회
    User getUserById(Long user_id);

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    User getAdminUserById(Long user_id);

    // 관리자의 사용자 정보 리스트 조회
    List<User> getUserList(Optional<String> keyword);

    // 관리자의 사용자 정보 수정
    User updateUser(Long user_id, UpdateUserDto updateUserDto);

    // 관리자의 사용자 정보 등록
    User createUser(CreateUserDto userCreateDto);

    // 관리자의 사용자 정보 삭제
    void deleteUser(Long user_id);
}
