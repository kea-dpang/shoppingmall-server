package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.base.Role;
import com.example.shoppingmallserver.entity.auth.Auth;
import lombok.Getter;

@Getter
public class RegisterRequestDto {
    private final String email;
    private final String password;
    private final Role role;

    public RegisterRequestDto(Auth auth) {
        this.email = auth.getEmail();
        this.password = auth.getPassword();
        this.role = auth.getRole();
    }
}
