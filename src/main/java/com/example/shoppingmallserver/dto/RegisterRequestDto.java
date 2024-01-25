package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.base.Role;
import com.example.shoppingmallserver.entity.auth.Auth;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterRequestDto {
    private final String email;
    private final String password;
    private final Role role;
    private final Long employeeNumber;
    private final String name;
    private final LocalDate joinDate;


    public RegisterRequestDto(User user, UserDetail userDetail) {
        email = user.getEmail();
        password = user.getPassword();
        role = user.getRole();
        employeeNumber = userDetail.getEmployeeNumber();
        name = userDetail.getName();
        joinDate = userDetail.getJoinDate();
    }
}
