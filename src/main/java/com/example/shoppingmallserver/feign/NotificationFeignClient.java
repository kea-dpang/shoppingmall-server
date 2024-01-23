package com.example.shoppingmallserver.feign;


import com.example.shoppingmallserver.dto.EmailNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Notification 서비스와의 통신을 위한 Feign 클라이언트 인터페이스입니다.
 * 이 인터페이스를 통해 Notification 서비스의 이메일 알림 API를 호출할 수 있습니다.
 */
@FeignClient(name = "notificationService", url = "${services.notification.url}")
public interface NotificationFeignClient {

    /**
     * 이메일 검증 코드를 전송하는 API를 호출하는 메서드입니다.
     * @param dto 이메일 알림에 필요한 데이터를 담은 DTO
     * @return API 호출의 결과를 담은 ResponseEntity 객체
     */
    @PostMapping("/api/notifications/email")
    ResponseEntity<String> sendEmailVerificationCode(@RequestBody EmailNotificationDto dto);
}
