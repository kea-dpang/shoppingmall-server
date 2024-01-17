package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.UserDetail;

import java.time.LocalDate;

public class AdminReadUserDto {
    private final Long user_id;
    private final Long employee_number;
    private final String name;
    private final String email;
    private final LocalDate join_date;
    private final String default_address;

    public AdminReadUserDto (UserDetail user_detail) {
        this.user_id = user_detail.getUser().getUser_id();
        this.employee_number = user_detail.getEmployee_number();
        this.name = user_detail.getName();
        this.email = user_detail.getEmail();
        this.join_date = user_detail.getJoin_date();
        this.default_address = "[" + user_detail.getZip_code() + "]"
                + user_detail.getAddress() + " " +
                user_detail.getDetail_address();
    }
}
