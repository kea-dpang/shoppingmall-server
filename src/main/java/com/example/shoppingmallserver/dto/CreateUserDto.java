package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.UserDetail;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateUserDto {

    private final Long employeeNumber;
    private final String email;
    private final String name;
    private final LocalDate joinDate;

    public CreateUserDto(UserDetail userDetail) {
        this.employeeNumber = userDetail.getEmployeeNumber();
        this.email = userDetail.getEmail();
        this.name = userDetail.getName();
        this.joinDate = userDetail.getJoinDate();
    }

}
