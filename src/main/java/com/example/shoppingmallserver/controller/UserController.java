package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.BaseResponse;
import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.service.Category;
import com.example.shoppingmallserver.service.TokenService;
import com.example.shoppingmallserver.service.UserService;

import com.example.shoppingmallserver.utils.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 정보를 관리하는 Controller 클래스입니다.
 * 사용자 정보 조회, 생성, 관리자에 의한 조회 및 삭제 기능을 제공합니다.
 */
@Tag(name = "User", description = "User 서비스 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    /**
     * 사용자를 등록하는 API입니다.
     *
     * @param requestDto 사용자 등록 요청 정보
     * @return HTTP 상태 코드 201 (CREATED)
     */
    @PostMapping("/register")
    @Operation(summary = "사용자 등록", description = "사용자를 생성합니다.")
    public ResponseEntity<BaseResponse> register(@RequestBody @Parameter(description = "사용자 등록 정보") RegisterRequestDto requestDto) {

        // 사용자 등록
        userService.register(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getRole(),
                requestDto.getEmployeeNumber(),
                requestDto.getName(),
                requestDto.getJoinDate()
        );

        // 성공 응답 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "사용자가 성공적으로 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    /**
     * 사용자 로그인 요청을 처리하는 API
     *
     * @param loginRequestDto 사용자가 입력한 이메일과 비밀번호 정보를 담고 있는 DTO
     * @return 사용자의 식별자를 담은 ResponseEntity. 사용자 인증에 실패했을 경우 400 상태 코드를 반환
     */
    @PostMapping("/login")
    @Operation(summary = "사용자 로그인", description = "사용자의 정보로 로그인합니다.")
    public ResponseEntity<SuccessResponse<Token>> login(@RequestBody @Parameter(description = "사용자 로그인 정보") LoginRequestDto loginRequestDto) throws Exception {

        // userService를 이용해 사용자 인증 정보 검증
        Long userId = userService.verifyUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 인증된 사용자에 대한 JWT 토큰 생성
        Token token = tokenService.createToken(userId);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "성공적으로 로그인 하였습니다.", token),
                HttpStatus.OK
        );
    }

    /**
     * 사용자가 요청한 이메일로 인증 코드를 전송합니다.
     *
     * @param email 인증 코드를 전송할 이메일
     * @return 인증 코드 전송 성공 메시지와 HTTP 상태 코드를 포함하는 응답 엔티티
     */
    @PostMapping("/send-verification-code")
    @Operation(summary = "이메일로 인증코드 전송", description = "사용자의 이메일 정보로 인증코드를 전송합니다.")
    public ResponseEntity<BaseResponse> sendVerificationCode(@RequestBody @Parameter(description = "사용자 이메일") String email) {

        // 이메일로 인증코드 전송
        userService.requestPasswordReset(email);

        // 전송했다는것 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.OK.value(), "인증코드 전송에 성공하였습니다."),
                HttpStatus.OK
        );
    }

    /**
     * 사용자가 제공한 이메일과 인증 코드, 새로운 비밀번호를 이용해 비밀번호를 재설정합니다.
     * 인증 코드가 일치하지 않거나, 인증 코드가 존재하지 않는 경우 예외가 발생합니다.
     *
     * @param email      사용자의 이메일
     * @param code       사용자가 제공한 인증 코드
     * @param newPassword 사용자의 새로운 비밀번호
     * @return 성공 응답을 포함한 ResponseEntity. 비밀번호 재설정이 성공하면 200 상태 코드와 성공 메시지를 반환
     */
    @PostMapping("/reset-password")
    @Operation(summary = "사용자 비밀번호 재설정", description = "인증코드가 제대로 되었을 때, 사용자가 입력한 비밀번호로 재설정합니다.")
    public ResponseEntity<BaseResponse> resetPassword(@RequestParam @Parameter(description = "사용자 이메일", example = "qwer1234@naver.com") String email, @RequestParam @Parameter(description = "사용자에게 보낸 인증 코드", example = "1234") String code, @RequestParam @Parameter(description = "사용자가 입력한 새 비밀번호", example = "qwer1234!@#$") String newPassword) {

        // 이메일, 인증 코드, 새로운 비밀번호로 비밀번호 재설정
        userService.verifyCodeAndResetPassword(email, code, newPassword);

        // 성공 응답 생성 및 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.OK.value(), "비밀번호가 재설정되었습니다."),
                HttpStatus.OK
        );
    }

    /**
     * 사용자가 제공한 이메일과 기존 비밀번호, 새로운 비밀번호를 이용해 비밀번호를 변경합니다.
     *
     * @param email      사용자의 이메일
     * @param oldPassword 사용자가 제공한 기존 비밀번호
     * @param newPassword 사용자의 새로운 비밀번호
     * @return 성공 응답을 포함한 ResponseEntity. 비밀번호 변경이 성공하면 200 상태 코드와 성공 메시지를 반환
     */
    @PostMapping("/change-password")
    @Operation(summary = "사용자 비밀번호 변경", description = "사용자가 입력한 비밀번호로 변경합니다.")
    public ResponseEntity<BaseResponse> changePassword(@RequestParam @Parameter(description = "사용자 이메일", example = "qwer1234@naver.com") String email, @RequestParam @Parameter(description = "사용자의 이전 비밀번호", example = "qwer1234!@#$") String oldPassword, @RequestParam @Parameter(description = "사용자가 입력한 새 비밀번호", example = " newqwer1234!@#$") String newPassword) {

        // 이메일, 기존 비밀번호, 새로운 비밀번호로 비밀번호 변경
        userService.changePassword(email, oldPassword, newPassword);

        // 성공 응답 생성 및 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.OK.value(), "비밀번호가 재설정되었습니다."),
                HttpStatus.OK
        );
    }

    /**
     * 클라이언트가 제공한 사용자ID를 이용하여 계정을 삭제하고, 성공 메시지를 반환합니다.
     * 계정 삭제가 실패하면 UserNotFoundException이 발생하게 됩니다.
     *
     * @param id 삭제할 계정의 식별자
     * @return 성공 응답을 포함한 ResponseEntity. 계정 삭제가 성공하면 200 상태 코드와 성공 메시지를 반환
     */
    @DeleteMapping("/delete-account/{id}")
    @Operation(summary = "사용자 탈퇴", description = "사용자가 탈퇴를 했을 때, 정보를 삭제합니다.")
    public ResponseEntity<BaseResponse> deleteAccount(@PathVariable @Parameter(description = "사용자 ID(PK)", example = "1") Long id, @RequestBody WithdrawalDto withdrawalDto) {

        // 사용자 ID로 계정 삭제
        userService.deleteAccount(id, withdrawalDto.getOldPassword(), withdrawalDto.getReason(), withdrawalDto.getMessage());

        // 성공 응답 생성 및 반환
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.OK.value(), "계정이 삭제되었습니다."),
                HttpStatus.OK
        );
    }

    /**
     * 사용자 ID를 기반으로 사용자의 상세 정보를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 성공 응답 메시지와 함께 조회한 사용자 정보를 담은 DTO를 반환
     */
    @GetMapping("/{userId}")
    @Operation(summary = "사용자 상세 정보 조회", description = "사용자의 상세 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<ReadUserDto>> getUser(@PathVariable @Parameter(description = "사용자 ID(PK)", example = "1") Long userId) {

        // 사용자 ID를 기반으로 사용자의 상세 정보를 조회
        UserDetail userDetail = userService.getUserById(userId);

        // 조회한 사용자 정보를 이용하여 응답 DTO를 생성
        ReadUserDto data = new ReadUserDto(userDetail);

        // 생성한 응답 DTO를 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보가 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );

    }

    /**
     * 사용자 ID를 기반으로 사용자의 주소를 변경합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 성공 응답 메시지와 204코드 반환
     */
    @PatchMapping("/{userId}/address")
    @Operation(summary = "사용자 주소 변경", description = "사용자가 입력한 주소로 변경합니다.")
    public ResponseEntity<SuccessResponse<Void>> updateAddress(@PathVariable @Parameter(description = "사용자 ID(PK)", example = "1") Long userId, @RequestBody @Parameter(description = "사용자가 입력한 주소") AddressDto addressDto) {

        // 받은 정보를 통해 주소지 변경
        userService.updateAddress(userId, addressDto.getPhoneNumber(), addressDto.getZipCode(), addressDto.getAddress(), addressDto.getDetailAddress());

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
    @Operation(summary = "(관리자) 사용자 상세 정보 조회", description = "관리자가 사용자 상세 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<AdminReadUserDto>> adminGetUser(@PathVariable @Parameter(description = "사용자 ID(PK)", example = "1") Long userId) {

        // 사용자 ID를 기반으로 사용자의 상세 정보를 조회
        UserDetail userDetail = userService.getAdminUserById(userId);
        User user = userDetail.getUser();

        // 조회한 사용자 정보를 이용하여 응답 DTO를 생성
        AdminReadUserDto data = new AdminReadUserDto(user, userDetail);

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
     * @param category 사용자 정보에서 검색할 키워드
     * @return 성공 응답 메시지와 함께 조회한 사용자 정보 목록을 담은 DTO 목록을 반환
     */
    @GetMapping("/admin/find")
    @Operation(summary = "(관리자) 사용자 정보 목록 조회", description = "관리자가 사용자 정보 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<List<AdminReadUserListDto>>> adminGetUserList(@RequestParam @Parameter(description = "(관리자) 사용자 검색 카테고리", example = "NULL") Category category, @RequestParam(required = false) @Parameter(description = "(관리자) 사용자 검색 키워드", example = "김디팡") String keyword, Pageable pageable) {

        // 키워드를 기반으로 사용자 정보 목록을 조회 (관리자용)
        List<AdminReadUserListDto> userDetails = userService.getUserList(category, keyword, pageable);

        // 생성한 응답 DTO 목록을 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보 목록이 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", userDetails),
                HttpStatus.OK
        );
    }

    /**
     * 관리자가 요청 본문으로 받은 사용자 ID 목록을 이용하여 해당 사용자들을 삭제합니다.
     *
     * @param deleteListDto 삭제할 사용자의 ID 리스트
     * @return 성공 응답 메시지와 함께 삭제된 사용자의 ID 목록을 반환
     */
    @DeleteMapping("/admin/delete")
    @Operation(summary = "(관리자) 사용자 삭제", description = "관리자가 사용자를 삭제합니다.")
    public ResponseEntity<SuccessResponse<String>> adminDeleteUser(@RequestBody @Parameter(description = "사용자 ID(PK) 목록") DeleteListDto deleteListDto) {

        // 요청 본문으로 받은 사용자 ID 목록을 이용하여 해당 사용자들을 삭제
        userService.deleteUser(deleteListDto.getUserIds());

        // 삭제한 사용자 ID 목록을 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보가 성공적으로 삭제되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 삭제하였습니다.", "Deleted user IDs: " + deleteListDto.getUserIds()),
                HttpStatus.OK
        );
    }

    //==============================Feign요청=======================

    // 상품 서비스에서의 리뷰 이름 요청
    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<SuccessResponse<String>> getReviewer(@PathVariable Long reviewerId) {

        // 사용자의 이름 전달
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "리뷰어의 이름 전달 성공", userService.getReviewer(reviewerId).getUserDetail().getName()),
                HttpStatus.OK
        );
    }

    // QNA 서비스에서의 작성자 정보(이름, 이메일) 요청
    @GetMapping("/qna-find/{authorId}")
    public ResponseEntity<SuccessResponse<QnaAuthorDto>> getQnaAuthor(@PathVariable Long authorId) {

        // 사용자의 이름 전달
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "작성자의 정보 전달 성공", userService.getQnaReviewer(authorId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/list")
    @Operation(summary = "(백엔드) 사용자 상세 정보 리스트로 조회", description = "백엔드에서 사용자 상세 정보 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<AdminReadUserListDto>>> adminGetUserList(@RequestParam List<Long> userIds) {

        // Auth 서비스에서 이쪽으로 전해줄 DTO를 받아서 유저 아이디 리스트로 유저 정보 리스트를 요청
        List<UserDetail> userDetails = userService.getUserList(userIds);

        // DTO 리스트 생성
        List<AdminReadUserListDto> data = userDetails.stream().map(AdminReadUserListDto::new).toList();

        // 생성한 응답 DTO 목록을 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 사용자 정보 목록이 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );

    }

}
