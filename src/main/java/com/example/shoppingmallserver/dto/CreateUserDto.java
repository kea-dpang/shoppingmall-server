package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 회원가입 시 사용자 정보를 등록하기 위한 DTO 클래스입니다.
 * 사원 번호, 이메일, 이름, 가입일 정보를 포함합니다.
 */
@Getter
public class CreateUserDto {
    private final Long userId;
    private final Long employeeNumber;
    private final String email;
    private final String name;
    private final LocalDate joinDate;

    /**
     * UserDetail 엔티티를 이용하여 CreateUserDto를 생성합니다.
     *
     * @param userDetail 회원가입 시 등록하는 사용자의 상세 정보를 담은 엔티티
     */
    public CreateUserDto(User user, UserDetail userDetail) {
        this.userId = user.getUserId();
        this.employeeNumber = userDetail.getEmployeeNumber();
        this.email = userDetail.getEmail();
        this.name = userDetail.getName();
        this.joinDate = userDetail.getJoinDate();
    }

}
