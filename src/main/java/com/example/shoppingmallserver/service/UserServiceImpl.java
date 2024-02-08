package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.response.user.AdminReadUserListResponseDto;
import com.example.shoppingmallserver.entity.cart.Cart;
import com.example.shoppingmallserver.entity.user.*;
import com.example.shoppingmallserver.entity.wishlist.Wishlist;
import com.example.shoppingmallserver.exception.*;
import com.example.shoppingmallserver.feign.auth.AuthFeignClient;
import com.example.shoppingmallserver.feign.item.ItemFeignClient;
import com.example.shoppingmallserver.feign.mileage.MileageFeignClient;
import com.example.shoppingmallserver.feign.order.OrderFeignClient;
import com.example.shoppingmallserver.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserWithdrawalRepository userWithdrawalRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;

    /**
     * 마일리지, 상품, 주문, 인증과 관련된 기능을 제공하는 Feign 클라이언트입니다.
     * 이 클라이언트를 사용해 각 서비스와 통신할 수 있습니다.
     */
    private final MileageFeignClient mileageFeignClient;
    private final ItemFeignClient itemFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final AuthFeignClient authFeignClient;



    @Override
    public void register(String email, Long employeeNumber, String name, LocalDate joinDate) {

        // 이메일로 사용자를 찾고 사용자가 이미 존재하는 경우 예외 발생
        // 변수 인라인화
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException(email);
                });

        // 사용자 정보 생성
        User newUser = User.builder()
                .email(email)
                .status(UserStatus.USER)
                .build();

        // 새로운 유저의 정보 생성
        UserDetail newUserDetail = UserDetail.builder()
                .user(newUser)
                .employeeNumber(employeeNumber)
                .name(name)
                .joinDate(joinDate)
                .phoneNumber("") // 기본값, 실제로는 사용자로부터 전화번호를 받아야 합니다.
                .zipCode("") // 기본값, 실제로는 사용자로부터 우편번호를 받아야 합니다.
                .address("") // 기본값, 실제로는 사용자로부터 주소를 받아야 합니다.
                .detailAddress("") // 기본값, 실제로는 사용자로부터 상세 주소를 받아야 합니다.
                .build();

        // 연관 관계 편의 메소드로 user와 userDetail을 서로 참조하도록 설정
        newUser.assignUserDetail(newUserDetail);

        // 사용자 및 정보 저장 후 생성 (사용자 식별자를 얻기 위해 미리 DB에 저장)
        // Cascade 설정으로 UserDetail도 함께 저장된다.
        userRepository.save(newUser);

        // 사용자의 마일리지 생성
        mileageFeignClient.createMileage(newUserDetail.getUser().getId(), newUserDetail.getUser().getId());

        log.info("사용자 생성 완료. 사용자 ID: {}", newUser.getId());
    }

    @Override
    public void deleteAccount(Long userId, List<String> reasons, String message) {

        // 식별자로 계정 조회. 계정이 존재하지 않으면 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<WithdrawalReason> enumReasons = new ArrayList<>();

        // reason을 String에서 enum으로 변경
        for(String reason : reasons) {
            enumReasons.add(WithdrawalReason.valueOf(reason));
        }

        // 유저 탈퇴 사유 생성
        UserWithdrawal userWithdrawal = UserWithdrawal
                .builder()
                .reason(enumReasons)
                .message(message)
                .withdrawalDate(LocalDate.now())
                .build();

        // 유저 탈퇴 사유 저장
        userWithdrawalRepository.save(userWithdrawal);

        // 계정, 정보, 장바구니, 위시리스트 삭제
        if(userRepository.existsById(userId)) {
            userRepository.delete(user);
        }

        UserDetail userDetail = user.getUserDetail();
        if(userDetail != null && userDetailRepository.existsById(userDetail.getId())) {
            userDetailRepository.delete(userDetail);
        }

        Cart cart = cartRepository.findCartByUserId(userId);
        if(cart != null && cartRepository.existsById(cart.getId())) {
            cartRepository.delete(cart);
        }

        Wishlist wishlist = wishlistRepository.findWishlistByUserId(userId);
        if(wishlist != null && wishlistRepository.existsById(wishlist.getId())) {
            wishlistRepository.delete(wishlist);
        }

        // 마일리지 삭제
        //mileageFeignClient.deleteMileage(userId, userId);

        log.info("탈퇴 성공 후 탈퇴 사유 생성 성공. 탈퇴 ID: {}. 탈퇴 사유: {}", userWithdrawal.getId(), userWithdrawal.getReason());
    }

    // 사용자 정보 조회
    @Override
    public UserDetail getUserById(Long userId) {
        log.info("사용자 정보 조회 성공. 사용자 아이디: {}", userId);
        return userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    // 사용자 주소 변경
    public void updateAddress(Long userId, String phoneNumber, String zipCode, String address, String detailAddress) {
        // 사용자 정보 찾기
        UserDetail userDetail = userDetailRepository.findByUserId(userId);
        // 엔티티 변경
        userDetail.changeAddress(phoneNumber, zipCode, address, detailAddress);
        // 변경된 내용을 데이터베이스에 반영
        userDetailRepository.save(userDetail);
        log.info("사용자 주소 변경 성공. 사용자 ID: {}", userDetail.getUser().getId());
    }

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    @Override
    public UserDetail getAdminUserById(Long userId) {
        log.info("사용자 정보 조회 성공. 사용자 아이디: {}", userId);
        return userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    // 관리자의 사용자 정보 리스트 조회
    @Override
    public Page<AdminReadUserListResponseDto> getUserList(Category category, String keyword, Pageable pageable) {

        // 키워드가 있는 경우
        if(keyword != null) {
            switch (category) {
                case EMPLOYEENUMBER -> {
                    if(keyword.matches("\\d+")) {
                        Long employeeNumber = Long.parseLong(keyword);
                        Page<UserDetail> userIds = userDetailRepository.findByEmployeeNumber(employeeNumber, pageable);
                        log.info("키워드 조건에 따른 사용자 정보 조회 성공. 조건: {}. 키워드: {}.", category, keyword);
                        return userIds.map(AdminReadUserListResponseDto::new);
                    }
                    else {
                        return null;
                    }
                }
                case EMAIL -> {
                    Page<User> userIds = userRepository.findByEmailContaining(keyword, pageable);
                    log.info("조건에 따른 사용자 정보 조회 성공. 조건: {}", keyword);
                    return userIds.map(this::convertToDto);
                }
                case NAME -> {
                    Page<UserDetail> userIds = userDetailRepository.findByNameContaining(keyword, pageable);
                    log.info("키워드 조건에 따른 사용자 정보 조회 성공. 조건: {}. 키워드: {}.", category, keyword);
                    return userIds.map(AdminReadUserListResponseDto::new);
                }
                case ALL -> {
                    Page<UserDetail> userIds = userDetailRepository.findAll(pageable);
                    log.info("사용자 전체 정보 조회 성공.");
                    return userIds.map(AdminReadUserListResponseDto::new);
                }
            }
        }
        else {
            Page<UserDetail> userIds = userDetailRepository.findAll(pageable);
            log.info("사용자 전체 정보 조회 성공.");
            return userIds.map(AdminReadUserListResponseDto::new);
        }
        Page<UserDetail> userIds = userDetailRepository.findAll(pageable);
        log.info("사용자 전체 정보 조회 성공.");
        return userIds.map(AdminReadUserListResponseDto::new);
    }

    private AdminReadUserListResponseDto convertToDto(User user) {
        return new AdminReadUserListResponseDto(user.getUserDetail());
    }

    // 관리자의 사용자 정보 삭제
    @Override
    public void deleteUser(List<Long> userIds) {

        // 사용자 목록 별로 조회
        for (Long userId : userIds) {
            // 사용자 조회, 없을 경우 예외 발생
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

            // 사용자 삭제(관련된 사용자 서비스 내 데이터 삭제)
            userRepository.delete(user);
            cartRepository.delete(user.getCart());
            wishlistRepository.delete(user.getWishlist());

            // 사용자가 남긴 모든 기록 삭제
            mileageFeignClient.deleteMileage(userId, userId);
            itemFeignClient.deleteReview(userId);
            // Todo: orderService 삭제

            log.info("사용자 정보 삭제 성공. 삭제된 사용자 아이디: {}", userIds);
        }
    }

    //==============================Feign요청=======================

    // 인증 서비스에서의 사용자 리스트 요청
    public List<UserDetail> getUserList(List<Long> userIds) {

        // 아이디 리스트로 유저 정보를 검색
        List<UserDetail> userDetails = userDetailRepository.findAllByIdIn(userIds);

        // 유저 정보가 비어있을 경우
        if(userDetails.isEmpty()) {
            throw new UserNotFoundException("제공된 아이디들에 대한 유저를 찾을 수 없습니다.");
        }

        return userDetails;
    }
}
