package com.example.shoppingmallserver.entity.user;

/**
 * 사용자의 상태를 나타내는 열거형 클래스입니다.
 * 사용자 상태는 'USER'(사용자)와 'SECESSION'(탈퇴) 두 가지로 구분됩니다.
 */
public enum UserStatus {
    USER,  // 사용자
    SECESSION  // 탈퇴
}