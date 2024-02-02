package com.example.shoppingmallserver.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "회원가입 정보")
@Getter
public class RegisterRequestDto {
    @Schema(description = "이메일", example = "qwer1234@naver.com")
    private final String email;
    @Schema(description = "사원번호", example = "1")
    private final Long employeeNumber;
    @Schema(description = "이름", example = "김디팡")
    private final String name;
    @Schema(description = "입사날짜", example = "2024-01-01")
    private final LocalDate joinDate;


    public RegisterRequestDto(String email, Long employeeNumber, String name, LocalDate joinDate) {
        this.email = email;
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.joinDate = joinDate;
    }
}
