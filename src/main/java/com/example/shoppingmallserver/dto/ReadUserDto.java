package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.User;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadUserDto {

    private final Long employeeNumber;
    private final String name;
    private final String email;
    private final LocalDate join_date;

    public ReadUserDto(User user) {
        this.employeeNumber = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.join_date = user.getJoin_date();
    }
}