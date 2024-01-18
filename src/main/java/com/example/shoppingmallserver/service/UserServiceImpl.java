package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.CreateUserDto;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.entity.user.UserStatus;
import com.example.shoppingmallserver.exception.EmailAlreadyExistsException;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    private final MileageRepository mileageRepository;

    // 사용자 정보 조회
    @Override
    public UserDetail getUserById(Long userId) {
        return userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    @Override
    public UserDetail createUser(CreateUserDto createUserDto) {

        // 이메일 중복 확인 (중복일 경우 예외를 던진다)
        userDetailRepository.findByEmail(createUserDto.getEmail())
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyExistsException(createUserDto.getEmail());
                });

        // 새로운 유저 생성
        User user = User.builder()
                .status(UserStatus.USER)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        // 새로운 유저의 정보 생성
        UserDetail userDetail = UserDetail.builder()
                .user(user)
                .employeeNumber(createUserDto.getEmployeeNumber())
                .email(createUserDto.getEmail())
                .name(createUserDto.getName())
                .joinDate(createUserDto.getJoinDate())
                .phoneNumber("") // 기본값, 실제로는 사용자로부터 전화번호를 받아야 합니다.
                .zipCode("") // 기본값, 실제로는 사용자로부터 우편번호를 받아야 합니다.
                .address("") // 기본값, 실제로는 사용자로부터 주소를 받아야 합니다.
                .detailAddress("") // 기본값, 실제로는 사용자로부터 상세 주소를 받아야 합니다.
                .build();

        userRepository.save(user);

        return  userDetailRepository.save(userDetail);
    }

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    @Override
    public UserDetail getAdminUserById(Long userId) {
        return userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    // 관리자의 사용자 정보 리스트 조회
    @Override
    public List<UserDetail> getUserList(String keyword) {

        if(keyword != null) {
            return userDetailRepository.findByNameContaining(keyword);
        }
        else {
            return userDetailRepository.findAll();
        }
    }

    // 관리자의 사용자 정보 삭제
    @Override
    public void deleteUser(List<Long> userIds) {

        // 사용자 목록 별로 조회
        for(Long userId : userIds) {
            // 사용자 조회, 없을 경우 예외 발생
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

            // 사용자 삭제
            userRepository.delete(user);
        }
    }
}