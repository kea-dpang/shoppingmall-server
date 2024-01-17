package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.AdminReadUserDto;
import com.example.shoppingmallserver.dto.ReadUserDto;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<ReadUserDto>> getUser(@PathVariable Long user_id) {
        UserDetail user_detail = userService.getUserById(user_id);
        ReadUserDto data = new ReadUserDto(user_detail);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }

    // ==========================관리자===========================

    @GetMapping("/admin/{userId}")
    public ResponseEntity<SuccessResponse<AdminReadUserDto>> adminGetUser(@PathVariable Long user_id) {
        UserDetail user_detail = userService.getAdminUserById(user_id);
        AdminReadUserDto data = new AdminReadUserDto(user_detail);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }
}
