package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.UserDetail;

import java.time.LocalDate;

public class AdminReadUserDto {
    private final Long userId;
    private final Long employeeNumber;
    private final String name;
    private final String email;
    private final LocalDate joinDate;
    private final String defaultAddress;

    public AdminReadUserDto (UserDetail user_detail) {
        this.userId = user_detail.getUser().getUserId();
        this.employeeNumber = user_detail.getEmployeeNumber();
        this.name = user_detail.getName();
        this.email = user_detail.getEmail();
        this.joinDate = user_detail.getJoinDate();
        this.defaultAddress = user_detail.getZipCode() + " "
                + user_detail.getAddress() + " " +
                user_detail.getDetailAddress();
    }
}
