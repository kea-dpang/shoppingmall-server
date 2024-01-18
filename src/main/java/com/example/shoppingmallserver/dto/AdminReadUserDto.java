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
        this.userId = user_detail.getUser().getUser_id();
        this.employeeNumber = user_detail.getEmployee_number();
        this.name = user_detail.getName();
        this.email = user_detail.getEmail();
        this.joinDate = user_detail.getJoin_date();
        this.defaultAddress = user_detail.getZip_code() + " "
                + user_detail.getAddress() + " " +
                user_detail.getDetail_address();
    }
}
