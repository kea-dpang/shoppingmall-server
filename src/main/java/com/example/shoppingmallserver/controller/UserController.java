package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.AdminReadUserDto;
import com.example.shoppingmallserver.dto.AdminReadUserListDto;
import com.example.shoppingmallserver.dto.CreateUserDto;
import com.example.shoppingmallserver.dto.ReadUserDto;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 정보를 관리하는 Controller 클래스입니다.
 * 사용자 정보 조회, 생성, 관리자에 의한 조회 및 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 ID를 기반으로 사용자의 상세 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 성공 응답 메시지와 함께 조회한 사용자 정보를 담은 DTO를 반환
     */
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<ReadUserDto>> getUser(@PathVariable Long userId) {

        // 사용자 ID를 기반으로 사용자의 상세 정보를 조회
        UserDetail userDetail = userService.getUserById(userId);

        // 사용자 정보를 이용하여 사용자 생성
        User user = userDetail.getUser();

        // 조회한 사용자 정보를 이용하여 응답 DTO를 생성
        ReadUserDto data = new ReadUserDto(user, userDetail);

        // 생성한 응답 DTO를 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보가 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );

    }

    /**
     * 요청 본문으로 받은 사용자 정보를 이용하여 새로운 사용자를 생성합니다.
     *
     * @param createUserDto 생성할 사용자 정보를 담은 DTO
     * @return 성공 응답 메시지와 함께 생성된 사용자 정보를 담은 DTO를 반환
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateUserDto>> createUser(@RequestBody CreateUserDto createUserDto) {

        // 요청 본문으로 받은 사용자 정보를 이용하여 새로운 사용자 정보를 생성
        UserDetail userDetail = userService.createUser(createUserDto);

        // 사용자 정보를 이용하여 사용자 생성
        User user = userDetail.getUser();

        // 생성한 사용자 정보를 이용하여 응답 DTO를 생성
        CreateUserDto data = new CreateUserDto(user, userDetail);

        // 생성한 응답 DTO를 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보가 성공적으로 생성되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.CREATED.value(), "사용자 정보를 성공적으로 생성하였습니다.", data),
                HttpStatus.CREATED
        );

    }

    @PatchMapping("/{userId}/address")
    public ResponseEntity<SuccessResponse<Void>> updateAddress(@PathVariable Long userId, @RequestBody String zipCode, String address, String detailAddress) {

        // 받은 정보를 통해 주소지 변경
        userService.updateAddress(userId, zipCode, address, detailAddress);

        //  변경 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 주소가 성공적으로 변경되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.NO_CONTENT.value(), "사용자의 주소를 성공적으로 변경하였습니다.", null),
                HttpStatus.NO_CONTENT
        );
    }

    // ==========================관리자===========================

    /**
     * 관리자가 사용자 ID를 기반으로 사용자의 상세 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 성공 응답 메시지와 함께 조회한 사용자 정보를 담은 DTO를 반환
     */
    @GetMapping("/admin/{userId}")
    public ResponseEntity<SuccessResponse<AdminReadUserDto>> adminGetUser(@PathVariable Long userId) {

        // 사용자 ID를 기반으로 사용자의 상세 정보를 조회
        UserDetail userDetail = userService.getAdminUserById(userId);

        // 조회한 사용자 정보를 이용하여 응답 DTO를 생성
        AdminReadUserDto data = new AdminReadUserDto(userDetail);

        // 생성한 응답 DTO를 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보가 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );

    }

    /**
     * 관리자가 키워드를 기반으로 사용자 정보 목록을 조회합니다.
     *
     * @param keyword 사용자 정보에서 검색할 키워드
     * @return 성공 응답 메시지와 함께 조회한 사용자 정보 목록을 담은 DTO 목록을 반환
     */
    @GetMapping("/admin/find")
    public ResponseEntity<SuccessResponse<List<AdminReadUserListDto>>> adminGetUserList(@RequestParam(value = "keyword", required = false) String keyword) {

        // 키워드를 기반으로 사용자 정보 목록을 조회 (관리자용)
        List<UserDetail> userDetails = userService.getUserList(keyword);

        // 조회한 사용자 정보 목록을 이용하여 응답 DTO 목록을 생성 (관리자용)
        List<AdminReadUserListDto> data = userDetails.stream().map(AdminReadUserListDto::new).toList();

        // 생성한 응답 DTO 목록을 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보 목록이 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );

    }

    /**
     * 관리자가 요청 본문으로 받은 사용자 ID 목록을 이용하여 해당 사용자들을 삭제합니다.
     *
     * @param userIds 삭제할 사용자들의 ID 목록
     * @return 성공 응답 메시지와 함께 삭제된 사용자의 ID 목록을 반환
     */
    @DeleteMapping("/admin/delete")
    public ResponseEntity<SuccessResponse<String>> adminDeleteUser(@RequestBody List<Long> userIds) {

        // 요청 본문으로 받은 사용자 ID 목록을 이용하여 해당 사용자들을 삭제
        userService.deleteUser(userIds);

        // 삭제한 사용자 ID 목록을 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보가 성공적으로 삭제되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 삭제하였습니다.", "Deleted user IDs: " + userIds),
                HttpStatus.OK
        );
    }
}
