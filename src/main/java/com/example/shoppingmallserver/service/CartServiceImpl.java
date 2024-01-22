package com.example.shoppingmallserver.service;


import com.example.shoppingmallserver.dto.AddCartItemInfoDto;
import com.example.shoppingmallserver.dto.PurchaseCartItemDto;
import com.example.shoppingmallserver.entity.cart.CartItem;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.repository.CartRepository;
import com.example.shoppingmallserver.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    // 장바구니 상품 조회
    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartRepository.findCartItemListByUserId(userId);
    }

    // 장바구니 상품 추가
    @Transactional
    @Override
    public CartItem addCartItem(Long userId, AddCartItemInfoDto itemInfo) {

        // userId에 해당하는 사용자 찾아오기
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        // 새 CartItem 생성
        CartItem cartItem = CartItem.builder()
                .user(user)
                .quantity(1)
                .addedAt(LocalDate.now())
                .build();

        // 저장
        cartRepository.save(cartItem);

        return cartRepository.findCartItemByUserId(userId);
    }

    // 장바구니 상품 삭제
    @Override
    public void deleteCartItem(Long userId, Long itemId) {
        cartRepository.deleteByUserIdAndCartItemId(userId, itemId);
    }

    // 장바구니 상품 구매
    @Override
    public List<CartItem> purchaseCartItem(Long userId, PurchaseCartItemDto purchaseCartItemDto) {
        return null;
    }

    // ==========================관리자===========================

}
