package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.response.feign.QnaAuthorDto;
import com.example.shoppingmallserver.dto.response.user.AdminReadUserListResponseDto;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.entity.user.WithdrawalReason;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Service
public interface UserService {

    /**
     * 계정 등록 메서드.
     *
     * 이메일, 비밀번호, 권한을 기반으로 새로운 계정을 생성합니다.
     * 권한을 별도로 지정하지 않은 경우, 기본적으로 'USER' 권한을 부여합니다.
     * 지정된 권한이 있는 경우, 해당 권한으로 계정을 생성합니다.
     */
    void register(String email, Long employeeNumber, String name, LocalDate joinDate);

    /**
     * 계정 삭제 메서드.
     *
     * 사용자의 고유 식별자를 기반으로 해당 사용자 계정을 삭제합니다.
     */
    void deleteAccount(Long userId, String oldPassword, WithdrawalReason reason, String message);

    // 사용자 정보 조회
    UserDetail getUserById(Long userId);

    void updateAddress(Long userId, String phoneNumber, String zipCode, String address, String detailAddress);

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    UserDetail getAdminUserById(Long userId);

    // 관리자의 사용자 정보 리스트 조회
    List<AdminReadUserListResponseDto> getUserList(Category category, String keyword, Pageable pageable);

    // 관리자의 사용자 정보 삭제
    void deleteUser(List<Long> userIds);

    //==============================Feign요청=======================

    // Auth 서비스에서 유저 리스트 요청
    List<UserDetail> getUserList(List<Long> userIds);
}
