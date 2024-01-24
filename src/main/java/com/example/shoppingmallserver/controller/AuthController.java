package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.BaseResponse;
import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.LoginRequestDto;
import com.example.shoppingmallserver.dto.RegisterRequestDto;
import com.example.shoppingmallserver.dto.Token;
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
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequestDto requestDto) {

        // 사용자 등록
        authService.register(requestDto.getEmail(), requestDto.getPassword(), requestDto.getRole());

        // 생성된 토큰과 함께 응답 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "사용자가 성공적으로 등록되었습니다."),
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
    public ResponseEntity<SuccessResponse<Token>> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {

            // 사용자가 입력한 이메일과 비밀번호를 이용해 UsernamePasswordAuthenticationToken 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            // AuthenticationManager를 이용해 생성한 토큰으로 사용자 인증 시도
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // authService를 이용해 사용자 인증 정보 검증
            Long userId = authService.verifyUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            // 인증된 사용자에 대한 JWT 토큰 생성
            Token jwtToken = jwtTokenProvider.createTokens(authentication, userId);

            return new ResponseEntity<>(
                    new SuccessResponse<>(HttpStatus.OK.value(), "성공적으로 로그인 하였습니다.", jwtToken),
                    HttpStatus.OK
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

    /**
     * 사용자가 제공한 이메일과 인증 코드, 새로운 비밀번호를 이용해 비밀번호를 재설정합니다.
     * 인증 코드가 일치하지 않거나, 인증 코드가 존재하지 않는 경우 예외가 발생합니다.
     *
     * @param email      사용자의 이메일
     * @param code       사용자가 제공한 인증 코드
     * @param newPassword 사용자의 새로운 비밀번호
     * @return 성공 응답을 포함한 ResponseEntity. 비밀번호 재설정이 성공하면 200 상태 코드와 성공 메시지를 반환
     */
    @PostMapping("/reset-password")
    public ResponseEntity<BaseResponse> resetPassword(@RequestParam String email, @RequestParam String code, @RequestParam String newPassword) {

        // 이메일, 인증 코드, 새로운 비밀번호로 비밀번호 재설정
        authService.verifyCodeAndResetPassword(email, code, newPassword);

        // 성공 응답 생성 및 반환
        return new ResponseEntity<>(
               new BaseResponse(HttpStatus.OK.value(), "비밀번호가 재설정되었습니다."),
                HttpStatus.OK
        );
    }

    /**
     * 사용자가 제공한 이메일과 기존 비밀번호, 새로운 비밀번호를 이용해 비밀번호를 변경합니다.
     *
     * @param email      사용자의 이메일
     * @param oldPassword 사용자가 제공한 기존 비밀번호
     * @param newPassword 사용자의 새로운 비밀번호
     * @return 성공 응답을 포함한 ResponseEntity. 비밀번호 변경이 성공하면 200 상태 코드와 성공 메시지를 반환
     */
    @PostMapping("/change-password")
    public ResponseEntity<BaseResponse> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {

        // 이메일, 기존 비밀번호, 새로운 비밀번호로 비밀번호 변경
        authService.changePassword(email, oldPassword, newPassword);

        // 성공 응답 생성 및 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.OK.value(), "비밀번호가 재설정되었습니다."),
                HttpStatus.OK
        );
    }

    /**
     * 클라이언트가 제공한 식별자를 이용하여 계정을 삭제하고, 성공 메시지를 반환합니다.
     * 계정 삭제가 실패하면 AuthNotFoundException이 발생하게 됩니다.
     *
     * @param id 삭제할 계정의 식별자
     * @return 성공 응답을 포함한 ResponseEntity. 계정 삭제가 성공하면 200 상태 코드와 성공 메시지를 반환
     * @throws AuthNotFoundException 삭제할 계정이 존재하지 않는 경우 발생
     */
    @DeleteMapping("/delete-account/{id}")
    public ResponseEntity<BaseResponse> deleteAccount(@PathVariable Long id) {

        // 식별자로 계정 삭제
        authService.deleteAccount(id);

        // 성공 응답 생성 및 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.OK.value(), "계정이 삭제되었습니다."),
                HttpStatus.OK
        );
    }
}
