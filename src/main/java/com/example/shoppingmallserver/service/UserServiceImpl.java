package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.user.UserDetail;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.repository.*;

import lombok.RequiredArgsConstructor;

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
            UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

            // 장바구니와 위시리스트와 마일리지 삭제
            cartRepository.deleteById(userId);
            wishlistRepository.deleteById(userId);
            mileageRepository.deleteById(userId);

            // 사용자 삭제
            userDetailRepository.delete(userDetail);
            userRepository.delete(user);
        }
    }
}