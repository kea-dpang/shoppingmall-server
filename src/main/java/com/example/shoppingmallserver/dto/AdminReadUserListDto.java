package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.UserDetail;

import java.time.LocalDate;

public class AdminReadUserListDto {
    private final Long userId;
    private final Long employeeNumber;
    private final String name;
    private final String email;
    private final LocalDate joinDate;

    public AdminReadUserListDto(UserDetail userDetail) {
        this.userId = userDetail.getUser().getUserId();
        this.employeeNumber = userDetail.getEmployeeNumber();
        this.name = userDetail.getName();
        this.email = userDetail.getEmail();
        this.joinDate = userDetail.getJoinDate();
    }
}
