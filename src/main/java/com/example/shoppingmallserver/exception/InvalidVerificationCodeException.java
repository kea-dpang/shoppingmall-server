package com.example.shoppingmallserver.exception;

/**
 * 입력받은 인증 코드가 일치하지 않을 때 발생하는 예외.
 */
public class InvalidVerificationCodeException extends RuntimeException {

    /**
     * 주어진 이메일에 대해 입력받은 인증 코드가 일치하지 않을 때 예외를 발생시킵니다.
     *
     * @param email 인증 코드가 일치하지 않는 이메일
     */
    public InvalidVerificationCodeException(String email) {
        super("입력받은 인증 코드가 일치하지 않습니다: " + email);
    }
}
