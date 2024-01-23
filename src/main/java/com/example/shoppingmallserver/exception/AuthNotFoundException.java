package com.example.shoppingmallserver.exception;

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외입니다.
 * 이메일 또는 식별자를 통해 사용자를 찾을 수 없을 경우 이 예외가 발생합니다.
 */
public class AuthNotFoundException extends RuntimeException {

    /**
     * 이메일을 통해 사용자를 찾을 수 없을 때 이 예외를 생성합니다.
     *
     * @param email 찾을 수 없는 사용자의 이메일
     */
    public AuthNotFoundException(String email) {
        super("해당 이메일을 가진 사용자를 찾을 수 없습니다: " + email);
    }

    /**
     * 식별자를 통해 사용자를 찾을 수 없을 때 이 예외를 생성합니다.
     *
     * @param identifier 찾을 수 없는 사용자의 식별자
     */
    public AuthNotFoundException(Long identifier) {
        super("해당 식별자를 가진 사용자를 찾을 수 없습니다: " + identifier);
    }
}