package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.UserDetail;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadUserDto {

    private final Long employeeNumber;
    private final String name;
    private final String email;
    private final LocalDate join_date;

    public ReadUserDto(UserDetail user_detail) {
        this.employeeNumber = user_detail.getEmployee_number();
        this.name = user_detail.getName();
        this.email = user_detail.getEmail();
        this.join_date = user_detail.getJoin_date();
    }
}