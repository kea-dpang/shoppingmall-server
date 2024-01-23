package com.example.shoppingmallserver.exception;

/**
 * 잘못된 리프레시 토큰을 사용할 때 발생하는 예외를 표현하는 클래스입니다.
 */
public class InvalidRefreshTokenException extends RuntimeException {

    /**
     * 잘못된 리프레시 토큰으로 예외를 생성합니다.
     *
     * @param token 사용자가 제공한 잘못된 리프레시 토큰
     */
    public InvalidRefreshTokenException(String token) {
        super("Invalid refresh token: " + token);
    }
}
