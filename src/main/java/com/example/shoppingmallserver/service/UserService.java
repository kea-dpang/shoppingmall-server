package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.Cart;
import com.example.shoppingmallserver.entity.Mileage;
import com.example.shoppingmallserver.entity.User;

import com.example.shoppingmallserver.entity.Wishlist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    // 사용자 정보 조회
    User getUserById(Long user_id);

    // 장바구니 상품 조회
    List<Cart> getCartItemByUserId(Long user_id);

    // 장바구니 상품 추가
    Cart addCartItem(Long user_id, AddCartItemDto addCartItemDto);

    // 장바구니 상품 삭제
    void deleteCartItem(Long user_id);

    // 장바구니 상품 구매
    List<Cart> purchaseCartItem(Long id, PurchaseCartItemDto purchaseCartItemDto);

    // 마일리지 충전 신청
    Mileage requestMileageRecharge(Long user_id, ChargeMileageDto chargeMileageDto);

    // 위시리스트 상품 추가
    Wishlist addWishlistItem(Long user_id, AddWishlistItemDto addWishlistItemDto);

    // 위시리스트 목록 조회
    List<Wishlist> getWishlistItemList(Long user_id);

    // 위시리스트 상품 삭제
    void deleteWishlistItem(Long id);

    // ==========================관리자===========================

    // 관리자의 사용자 정보 조회
    User getAdminUserById(Long user_id);

    // 관리자의 사용자 정보 리스트 조회
    List<User> getUserList(Optional<String> keyword);

    // 관리자의 사용자 정보 수정
    User updateUser(Long user_id, UpdateUserDto updateUserDto);

    // 관리자의 사용자 정보 등록
    User createUser(CreateUserDto userCreateDto);

    // 관리자의 사용자 정보 삭제
    void deleteUser(Long user_id);

    // 관리자의 마일리지 신청자 리스트 조회
    List<User> getMileageList(Optional<String> keyword);

    // 관리자의 마일리지 신청 승인 상태 수정(자료형 수정 필)
    Mileage updateMileageStatus(Long user_id, UpdateMileageStatusDto updateMileageStatusDto);
}
