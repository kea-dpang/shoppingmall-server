package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.base.Role;
import com.example.shoppingmallserver.dto.EmailNotificationDto;
import com.example.shoppingmallserver.dto.QnaAuthorDto;
import com.example.shoppingmallserver.entity.user.*;
import com.example.shoppingmallserver.exception.*;
import com.example.shoppingmallserver.feign.MileageFeignClient;
import com.example.shoppingmallserver.feign.NotificationFeignClient;
import com.example.shoppingmallserver.redis.entity.VerificationCode;
import com.example.shoppingmallserver.redis.repository.VerificationCodeRepository;
import com.example.shoppingmallserver.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;

    /**
     * 알림과 관련된 기능을 제공하는 Feign 클라이언트입니다.
     * 이 클라이언트를 사용해 알림 서비스와 통신할 수 있습니다.
     */
    private final NotificationFeignClient notificationFeignClient;

    /**
     * 사용자 인증 코드를 관리하는 레포지토리입니다.
     * 이 레포지토리를 통해 인증 코드의 생성 및 검증 등의 작업을 수행할 수 있습니다.
     */
    private final VerificationCodeRepository verificationCodeRepository;

    /**
     * 비밀번호를 암호화하고 검증하는 역할을 하는 인코더입니다.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 마일리지와 관련된 기능을 제공하는 Feign 클라이언트입니다.
     * 이 클라이언트를 사용해 마일리지 서비스와 통신할 수 있습니다.
     */
    private final MileageFeignClient mileageFeignClient;

    @Override
    public void register(String email, String password, Role role, Long employeeNumber, String name, LocalDate joinDate) {

        // 이메일로 사용자를 찾고 사용자가 이미 존재하는 경우 예외 발생
        // 변수 인라인화
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException(email);
                });

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 사용자 정보 생성
        User newUser = User.builder()
                .email(email)
                .password(encodedPassword)
                .role(role)
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

    /**
     * 사용자의 이메일과 비밀번호를 검증하고, 검증이 성공하면 사용자의 고유 식별자를 반환합니다.
     *
     * @param email    검증할 사용자의 이메일
     * @param password 검증할 사용자의 비밀번호
     * @return 사용자의 고유 식별자
     * @throws UserNotFoundException    이메일에 해당하는 사용자를 찾을 수 없을 때 발생
     * @throws InvalidPasswordException 입력받은 비밀번호와 저장된 비밀번호가 일치하지 않을 때 발생
     */
    @Override
    public Long verifyUser(String email, String password) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // 입력받은 비밀번호와 저장된 비밀번호 비교
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException(email);
        }

        log.info("사용자 비밀번호 검증 완료. 사용자 ID: {}", user.getId());

        // 비밀번호가 일치하면 사용자의 고유 식별자 반환
        return user.getId();
    }

    /**
     * 비밀번호 재설정을 위한 인증번호를 요청하는 메소드
     *
     * @param email 사용자의 이메일 주소
     */
    @Override
    public void requestPasswordReset(String email) {
        try {
            // 자연수 4자리 인증번호 생성 (0부터 9999까지)
            String verificationCode = String.format("%04d", new Random().nextInt(10000));

            // 이메일 전송을 위한 DTO 객체 생성
            EmailNotificationDto dto = new EmailNotificationDto(
                    email,
                    "비밀번호 재설정 인증번호 안내",
                    "비밀번호 재설정을 위한 인증번호는 " + verificationCode + " 입니다."
            );

            // 이메일 전송
            ResponseEntity<String> response = notificationFeignClient.sendEmailVerificationCode(dto);

            // 이메일 전송이 성공하면 인증번호 저장
            if (response.getStatusCode().is2xxSuccessful()) {

                // 인증번호 저장을 위한 VerificationCode 객체 생성
                VerificationCode verificationCodeEntity = new VerificationCode(
                        email,
                        verificationCode
                );

                // 인증번호 저장
                verificationCodeRepository.save(verificationCodeEntity);

                log.info("인증번호 요청 성공. 사용자 이메일: {}", email);
            }
        } catch (Exception e) {
            log.error("비밀번호 재설정 인증번호 전송 중 알 수 없는 예외 발생", e);
            throw e;
        }
    }

    /**
     * 사용자가 제공한 인증 코드를 검증하고, 검증이 성공하면 사용자의 비밀번호를 새로운 비밀번호로 재설정합니다.
     * 인증 코드가 일치하지 않거나, 인증 코드가 존재하지 않는 경우 예외가 발생합니다.
     *
     * @param email       사용자의 이메일
     * @param code        사용자가 제공한 인증 코드
     * @param newPassword 사용자의 새로운 비밀번호
     * @throws UserNotFoundException             이메일에 해당하는 사용자가 존재하지 않는 경우
     * @throws VerificationCodeNotFoundException 제공된 이메일에 해당하는 인증 코드가 존재하지 않는 경우
     * @throws InvalidVerificationCodeException  제공된 인증 코드가 저장된 인증 코드와 일치하지 않는 경우
     */
    @Override
    public void verifyCodeAndResetPassword(String email, String code, String newPassword) {

        // 이메일로 전송된 유저 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // 이메일로 전송된 인증 코드 쿼리 확인
        VerificationCode storedCode = verificationCodeRepository.findById(email)
                .orElseThrow(() -> new VerificationCodeNotFoundException(email));

        // 입력받은 인증 코드와 저장된 인증 코드 비교
        if (!storedCode.getCode().equals(code)) {
            throw new InvalidVerificationCodeException(email);
        }

        // 인증 코드가 일치하면 비밀번호 변경
        user.updatePassword(passwordEncoder.encode(newPassword));

        // 인증코드 삭제
        verificationCodeRepository.delete(storedCode);

        log.info("비밀번호 변경 성공. 사용자 ID: {}", user.getId());
    }


    /**
     * 사용자의 비밀번호를 변경하는 메소드
     *
     * @param email       사용자의 이메일 주소
     * @param oldPassword 사용자의 현재 비밀번호
     * @param newPassword 사용자가 설정하려는 새 비밀번호
     * @throws UserNotFoundException    해당 이메일을 가진 사용자를 찾을 수 없을 경우 발생
     * @throws InvalidPasswordException 기존 비밀번호가 일치하지 않을 경우 발생
     */
    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        log.info("비밀번호 변경 요청. 이메일: " + email);

        // 이메일로 유저 찾기
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("해당 이메일을 가진 사용자를 찾을 수 없음: " + email);
            return new UserNotFoundException(email);
        });

        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            log.error("비밀번호 불일치: " + email);
            throw new InvalidPasswordException(email);
        }

        // 새 비밀번호 암호화 후 저장
        user.updatePassword(passwordEncoder.encode(newPassword));

        log.info("비밀번호 변경 성공. 사용자 ID: {}", user.getId());
    }

    @Override
    public void deleteAccount(Long userId, String oldPassword, WithdrawalReason reason, String message) {

        // 식별자로 계정 조회. 계정이 존재하지 않으면 예외 발생
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            log.error("비밀번호 불일치: " + user.getEmail());
            throw new InvalidPasswordException(user.getEmail());
        }

        // 유저 탈퇴 사유 생성
        UserWithdrawal userWithdrawal = UserWithdrawal
                .builder()
                .reason(reason)
                .message(message)
                .withdrawalDate(LocalDate.now())
                .build();

        // 계정, 정보, 장바구니, 위시리스트 삭제
        userRepository.delete(user);
        userDetailRepository.delete(user.getUserDetail());
        cartRepository.delete(cartRepository.findCartByUserId(userId));
        wishlistRepository.delete(wishlistRepository.findWishlistByUserId(userId));

        // 마일리지 삭제
        mileageFeignClient.deleteMileage(userId, userId);

        log.info("탈퇴 성공 후 탈퇴 사유 생성 성공. 탈퇴 ID: {}", userWithdrawal.getId());
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
    public List<UserDetail> getUserList(String keyword) {

        if (keyword != null) {
            log.info("사용자 정보 조회 성공.");
            return userDetailRepository.findByNameContaining(keyword);
        } else {
            log.info("사용자 정보 조회 성공.");
            return userDetailRepository.findAll();
        }
    }

    // 관리자의 사용자 정보 삭제
    @Override
    public void deleteUser(List<Long> userIds) {

        // 사용자 목록 별로 조회
        for (Long userId : userIds) {
            // 사용자 조회, 없을 경우 예외 발생
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

            // 사용자 삭제
            userRepository.delete(user);

            log.info("사용자 정보 삭제 성공. 삭제된 사용자 아이디: {}", userIds);
        }
    }

    //==============================Feign요청=======================

    // 상품 서비스에서의 리뷰 이름 요청
    @Override
    public User getReviewer(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public QnaAuthorDto getQnaReviewer(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return new QnaAuthorDto(user.getUserDetail().getName(), user.getEmail());
    }
}