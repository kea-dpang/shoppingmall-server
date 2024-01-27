package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.Token;

/**
 * 토큰 관련 서비스를 제공하는 인터페이스입니다.
 */
public interface TokenService {

    /**
     * 사용자 식별자를 기반으로 새로운 인증 토큰을 생성하는 메소드입니다.
     *
     * @param identifier 사용자의 식별자
     * @return 생성된 인증 토큰
     */
    Token createToken(Long identifier) throws Exception;

    /**
     * 사용자의 기존 리프레시 토큰을 입력 받아 새로운 인증 토큰을 생성하는 메소드입니다.
     *
     * @param accessToken 사용자의 기존 리프레시 토큰
     * @return 생성된 인증 토큰
     */
    Token refreshToken(String accessToken) throws Exception;

    /**
     * 사용자의 리프레시 토큰을 완전히 제거하는 메소드입니다. 이 메소드는 로그아웃 및 회원 탈퇴 시 사용됩니다.
     *
     * @param identifier 사용자의 식별자
     */
    void removeToken(Long identifier);
}
