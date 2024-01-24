package com.example.shoppingmallserver.exception;

/**
 * 비밀번호가 일치하지 않을 때 발생하는 예외입니다.
 * 사용자의 비밀번호가 입력된 비밀번호와 일치하지 않을 경우 이 예외가 발생합니다.
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * 비밀번호가 일치하지 않을 때 이 예외를 생성합니다.
     *
     * @param email 비밀번호가 일치하지 않는 사용자의 이메일
     */
    public InvalidPasswordException(String email) {
        super("비밀번호가 일치하지 않습니다: " + email);
    }
}
