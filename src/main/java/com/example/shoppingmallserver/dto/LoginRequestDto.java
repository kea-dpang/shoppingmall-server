package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.auth.Auth;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    private final String email;
    private final String password;

    public LoginRequestDto(Auth auth) {
        this.email = auth.getEmail();
        this.password = auth.getPassword();
    }
}
