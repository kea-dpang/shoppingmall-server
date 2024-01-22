package com.example.shoppingmallserver.exception;

/**
 * 이메일이 이미 존재할 때 발생하는 예외 클래스입니다.
 * 이메일이 이미 존재할 경우 이 예외가 발생하며, 메시지로 "이미 존재하는 이메일입니다: [이메일]"을 반환합니다.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    /**
     * 이미 존재하는 이메일을 파라미터로 받아서 예외 메시지를 생성합니다.
     *
     * @param email 이미 존재하는 이메일
     */
    public EmailAlreadyExistsException(String email) {
        super("이미 존재하는 이메일입니다: " + email);
    }

}
