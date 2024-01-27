package com.example.shoppingmallserver.exception;

/**
 * 토큰이 발견되지 않을 때 발생하는 예외를 표현하는 클래스입니다.
 */
public class TokenNotFoundException extends RuntimeException {

    /**
     * 주어진 식별자에 대한 토큰이 발견되지 않을 때 예외를 생성합니다.
     *
     * @param identifier 토큰을 찾을 수 없는 식별자
     */
    public TokenNotFoundException(Long identifier) {
        super("Token not found for identifier: " + identifier);
    }
}
