package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.RegisterRequestDto;
import com.example.shoppingmallserver.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 관련 요청을 처리하는 컨트롤러입니다.
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 사용자를 등록하는 API입니다.
     *
     * @param requestDto 사용자 등록 요청 정보
     * @return HTTP 상태 코드 201 (CREATED)
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Void>> register(@RequestBody RegisterRequestDto requestDto) {

        // 사용자 등록
        authService.register(requestDto.getEmail(), requestDto.getPassword(), requestDto.getRole());

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.CREATED.value(), "사용자가 성공적으로 등록되었습니다.", null),
                HttpStatus.CREATED
        );
    }

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
