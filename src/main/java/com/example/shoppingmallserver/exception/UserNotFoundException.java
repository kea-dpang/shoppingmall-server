package com.example.shoppingmallserver.exception;

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외 클래스입니다.
 * 사용자 ID로 사용자를 찾을 수 없을 경우 이 예외가 발생하며, 메시지로 "해당 ID의 사용자를 찾을 수 없습니다. ID: [사용자 ID]"를 반환합니다.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * 사용자 ID를 통해 사용자를 찾을 수 없을 때 이 예외를 생성합니다.
     *
     * @param userId 찾을 수 없는 사용자의 ID
     */
    public UserNotFoundException(Long userId) {
        super("해당 ID의 사용자를 찾을 수 없습니다. ID: " + userId);
    }

    /**
     * 일반적인 경우로 사용자를 찾을 수 없을 때 이 예외를 생성합니다.
     *
     * @param status 찾을 수 없는 경우의 예외 상태 설명
     */
    public UserNotFoundException(String status) {
        super(status);
    }
}