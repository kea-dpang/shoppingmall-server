package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.LoginRequestDto;
import com.example.shoppingmallserver.dto.RegisterRequestDto;
import com.example.shoppingmallserver.dto.Token;
import com.example.shoppingmallserver.entity.auth.Auth;
import com.example.shoppingmallserver.exception.AuthNotFoundException;
import com.example.shoppingmallserver.exception.InvalidPasswordException;
import com.example.shoppingmallserver.service.AuthService;

import com.example.shoppingmallserver.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;

/**
 * 인증 관련 요청을 처리하는 컨트롤러입니다.
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     * 사용자를 등록하는 API입니다.
     *
     * @param requestDto 사용자 등록 요청 정보
     * @return HTTP 상태 코드 201 (CREATED)
     */
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Token>> register(@RequestBody RegisterRequestDto requestDto) throws Exception {

        // 사용자 등록
        Auth newUser = authService.register(requestDto.getEmail(), requestDto.getPassword(), requestDto.getRole());

        // 등록된 사용자 로그인 처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );

        // JWT 토큰 생성 (사용자 등록 하자마자 로그인이 된다면 필요)
        Token jwtToken = jwtTokenProvider.createTokens(authentication, newUser.getUserIdx());

        // 생성된 토큰과 함께 응답 반환
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.CREATED.value(), "사용자가 성공적으로 등록되었습니다.", jwtToken),
                HttpStatus.CREATED
        );
    }

    /**
     * 사용자 로그인 요청을 처리하는 메소드.
     *
     * @param loginRequestDto 사용자가 입력한 이메일과 비밀번호 정보를 담고 있는 DTO
     * @return 사용자의 식별자를 담은 ResponseEntity. 사용자 인증에 실패했을 경우 400 상태 코드를 반환
     */
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        try {
            // 등록된 사용자 로그인 처리
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );
            Long userId = authService.verifyUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            Token jwtToken = jwtTokenProvider.createTokens(authentication, userId);
            return ResponseEntity.ok(userId);
        } catch (AuthNotFoundException | InvalidPasswordException e) {
            return ResponseEntity.badRequest().build();
        }
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
