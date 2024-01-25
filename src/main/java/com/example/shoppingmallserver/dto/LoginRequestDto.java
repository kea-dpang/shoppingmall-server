package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.user.User;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private final String email;
    private final String password;

    public LoginRequestDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
