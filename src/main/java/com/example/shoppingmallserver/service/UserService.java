package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    // 사용자 정보 조회
    UserDetail getUserById(Long userId);

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    UserDetail getAdminUserById(Long userId);

    // 관리자의 사용자 정보 리스트 조회
    List<UserDetail> getUserList(String keyword);

    // 관리자의 사용자 정보 삭제
    void deleteUser(List<Long> userIds);

}
