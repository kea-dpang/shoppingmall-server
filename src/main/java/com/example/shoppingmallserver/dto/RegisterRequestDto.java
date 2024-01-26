package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.base.Role;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "회원가입 정보")
@Getter
public class RegisterRequestDto {
    @Schema(description = "이메일", example = "qwer1234@naver.com")
    private final String email;
    @Schema(description = "비밀번호", example = "qwer1234!@#$")
    private final String password;
    @Schema(description = "역할", example = "USER")
    private final Role role;
    @Schema(description = "사원번호", example = "1")
    private final Long employeeNumber;
    @Schema(description = "이름", example = "김디팡")
    private final String name;
    @Schema(description = "입사날짜", example = "2024-01-01")
    private final LocalDate joinDate;


    public RegisterRequestDto(User user, UserDetail userDetail) {
        email = user.getEmail();
        password = user.getPassword();
        role = user.getRole();
        employeeNumber = userDetail.getEmployeeNumber();
        name = userDetail.getName();
        joinDate = userDetail.getJoinDate();
    }
}
