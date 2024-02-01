package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 정보")
@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @Schema(description = "이메일", example = "qwer1234@naver.com")
    private String email;
    @Schema(description = "비밀번호", example = "qwer1234!@#$")
    private String password;

    public LoginRequestDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
