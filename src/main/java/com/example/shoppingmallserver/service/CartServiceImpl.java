package com.example.shoppingmallserver.service;


import com.example.shoppingmallserver.entity.cart.Cart;
import com.example.shoppingmallserver.repository.CartRepository;
import com.example.shoppingmallserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    // 장바구니 상품 조회
    @Override
    public Cart getCartItemList(Long userId) {
        return cartRepository.findCartByUser_UserId(userId);
    }

    // 장바구니 상품 추가
    @Override
    public void addCartItem(Long userId, Long itemId) {

        // Cart에 상품 추가(빌더)
        Cart cart = Cart.builder()
                .itemId(itemId)
                .quantity(1)
                .build();

        // 저장
        cartRepository.save(cart);

        cartRepository.findCartByUser_UserId(userId);
    }

    // 장바구니 상품 삭제
    @Override
    public void deleteCartItem(Long userId, Long itemId) {
        cartRepository.deleteByUser_UserIdAndItemId(userId, itemId);
    }
}
