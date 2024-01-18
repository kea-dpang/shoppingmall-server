package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.AdminReadUserDto;
import com.example.shoppingmallserver.dto.AdminReadUserListDto;
import com.example.shoppingmallserver.dto.CreateUserDto;
import com.example.shoppingmallserver.dto.ReadUserDto;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final List<Long> userIds;

    // 사용자 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<ReadUserDto>> getUser(@PathVariable Long userId) {
        UserDetail userDetail = userService.getUserById(userId);
        ReadUserDto data = new ReadUserDto(userDetail);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }

    // 회원가입시 사용자 정보 등록 (회원가입 시 입력했던 정보를 등록)
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateUserDto>> createUser(@RequestBody CreateUserDto createUserDto) {
        UserDetail userDetail = userService.createUser(createUserDto);
        CreateUserDto data = new CreateUserDto(userDetail);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.CREATED.value(), "사용자 정보를 성공적으로 생성하였습니다.", data),
                HttpStatus.CREATED
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

    @DeleteMapping("/admin/delete")
    public ResponseEntity<SuccessResponse<String>> adminDeleteUser(@RequestBody List<Long> userIds) {
        userService.deleteUser(userIds);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 삭제하였습니다.", "Deleted user IDs: " + userIds),
                HttpStatus.OK
        );
    }
}
