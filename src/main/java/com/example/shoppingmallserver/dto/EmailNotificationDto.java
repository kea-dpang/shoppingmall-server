package com.example.shoppingmallserver.dto;

import lombok.Getter;

/**
 * 이메일 알림에 필요한 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 */
@Getter
public class EmailNotificationDto {
    private final String to;  // 이메일 수신자
    private final String title;  // 이메일 제목
    private final String body;  // 이메일 본문

    public EmailNotificationDto(String to, String title, String body) {
        this.to = to;
        this.title = title;
        this.body = body;
    }
}
