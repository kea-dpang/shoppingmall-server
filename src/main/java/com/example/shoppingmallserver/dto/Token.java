package com.example.shoppingmallserver.dto;

import lombok.Getter;

/**
 * 토큰 정보를 담는 클래스입니다.
 * 액세스 토큰과 리프레시 토큰을 포함합니다.
 */
@Getter
public class Token {

    private final String accessToken;  // 액세스 토큰
    private final String refreshToken;  // 리프레시 토큰

    /**
     * Token 클래스의 생성자입니다.
     *
     * @param accessToken  액세스 토큰
     * @param refreshToken 리프레시 토큰
     */
    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}