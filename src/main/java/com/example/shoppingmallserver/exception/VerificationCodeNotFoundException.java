package com.example.shoppingmallserver.exception;

/**
 * 이메일에 해당하는 인증 코드를 찾을 수 없을 때 발생하는 예외.
 */
public class VerificationCodeNotFoundException extends RuntimeException {

    /**
     * 주어진 이메일에 해당하는 인증 코드를 찾을 수 없을 때 예외를 발생시킵니다.
     *
     * @param email 인증 코드를 찾을 수 없는 이메일
     */
    public VerificationCodeNotFoundException(String email) {
        super("해당 이메일에 대한 인증 코드를 찾을 수 없습니다: " + email);
    }
}
