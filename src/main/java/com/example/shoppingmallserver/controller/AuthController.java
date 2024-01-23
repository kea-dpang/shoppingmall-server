package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 관련 요청을 처리하는 컨트롤러입니다.
 */
@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 사용자가 요청한 이메일로 인증 코드를 전송합니다.
     *
     * @param email 인증 코드를 전송할 이메일
     * @return 인증 코드 전송 성공 메시지와 HTTP 상태 코드를 포함하는 응답 엔티티
     */
    @PostMapping("/send-verification-code")
    public ResponseEntity<SuccessResponse<Void>> sendVerificationCode(@RequestParam String email) {

        // 이메일로 인증코드 전송
        authService.requestPasswordReset(email);

        // 전송했다는것 반환
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "인증코드 전송에 성공하였습니다.", null),
                HttpStatus.OK
        );
    }
}
