package com.example.shoppingmallserver.exception;

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외 클래스입니다.
 * 사용자 ID로 사용자를 찾을 수 없을 경우 이 예외가 발생하며, 메시지로 "해당 ID의 사용자를 찾을 수 없습니다. ID: [사용자 ID]"를 반환합니다.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * 찾을 수 없는 사용자의 ID를 파라미터로 받아서 예외 메시지를 생성합니다.
     *
     * @param userId 찾을 수 없는 사용자의 ID
     */
    public UserNotFoundException(Long userId) {
        super("해당 ID의 사용자를 찾을 수 없습니다. ID: " + userId);
    }

}