package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.base.Role;
import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;

import com.example.shoppingmallserver.entity.user.WithdrawalReason;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    /**
     * 계정 등록 메서드.
     *
     * 이메일, 비밀번호, 권한을 기반으로 새로운 계정을 생성합니다.
     * 권한을 별도로 지정하지 않은 경우, 기본적으로 'USER' 권한을 부여합니다.
     * 지정된 권한이 있는 경우, 해당 권한으로 계정을 생성합니다.
     */
    void register(String email, String password, Role role, Long employeeNumber, String name, LocalDate joinDate);

    /**
     * 사용자 검증 메서드.
     *
     * 사용자의 이메일과 비밀번호를 입력받아 사용자를 검증합니다.
     * 검증에 성공한 경우, 사용자의 고유 식별자인 userIdx를 반환합니다.
     */
    Long verifyUser(String email, String password);

    /**
     * 비밀번호 재설정 요청 메서드.
     *
     * 사용자의 이메일을 입력받아 비밀번호 재설정을 위한 인증번호를 생성하고,
     * 이를 사용자의 이메일로 전송합니다.
     */
    void requestPasswordReset(String email);

    /**
     * 인증번호 검증 및 비밀번호 재설정 메서드.
     *
     * 사용자의 이메일, 인증번호, 새로운 비밀번호를 입력받아 인증번호를 검증하고,
     * 인증번호가 유효한 경우 사용자의 비밀번호를 재설정합니다.
     */
    void verifyCodeAndResetPassword(String email, String code, String newPassword);

    /**
     * 비밀번호 변경 메서드.
     *
     * 사용자의 이메일과 기존 비밀번호, 새로운 비밀번호를 입력받아 비밀번호를 변경합니다.
     */
    void changePassword(String email, String oldPassword, String newPassword);

    /**
     * 계정 삭제 메서드.
     *
     * 사용자의 고유 식별자를 기반으로 해당 사용자 계정을 삭제합니다.
     */
    void deleteAccount(Long userId, String oldPassword, WithdrawalReason reason, String message);

    // 사용자 정보 조회
    UserDetail getUserById(Long userId);

    void updateAddress(Long userId, String zipCode, String address, String detailAddress);

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    UserDetail getAdminUserById(Long userId);

    // 관리자의 사용자 정보 리스트 조회
    List<UserDetail> getUserList(String keyword);

    // 관리자의 사용자 정보 삭제
    void deleteUser(List<Long> userIds);

    //==============================Feign요청=======================

    // 상품 서비스에서의 리뷰 이름 요청
    User getReviewer(Long userId);

}
