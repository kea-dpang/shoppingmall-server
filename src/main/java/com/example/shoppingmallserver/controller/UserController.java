package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.AdminReadUserDto;
import com.example.shoppingmallserver.dto.AdminReadUserListDto;
import com.example.shoppingmallserver.dto.ReadUserDto;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<ReadUserDto>> getUser(@PathVariable Long userId) {
        UserDetail user_detail = userService.getUserById(userId);
        ReadUserDto data = new ReadUserDto(user_detail);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    @GetMapping("/admin/{userId}")
    public ResponseEntity<SuccessResponse<AdminReadUserDto>> adminGetUser(@PathVariable Long userId) {
        UserDetail userDetail = userService.getAdminUserById(userId);
        AdminReadUserDto data = new AdminReadUserDto(userDetail);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }

    @GetMapping("/admin/find")
    public ResponseEntity<SuccessResponse<List<AdminReadUserListDto>>> adminGetUserList(@RequestParam(value = "keyword", required = false) String keyword) {
        List<UserDetail> userDetails = userService.getUserList(keyword);
        List<AdminReadUserListDto> data = userDetails.stream().map(AdminReadUserListDto::new).toList();

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }
}
