package com.example.shoppingmallserver.service;


import com.example.shoppingmallserver.dto.AddCartItemDto;
import com.example.shoppingmallserver.dto.PurchaseCartItemDto;
import com.example.shoppingmallserver.entity.cart.CartItem;
import com.example.shoppingmallserver.repository.CartRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    // 장바구니 상품 조회
    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // 장바구니 상품 추가
    @Override
    public CartItem addCartItem(Long userId, AddCartItemDto addCartItemDto) {
        return null;
    }

    // 장바구니 상품 삭제
    @Override
    public void deleteCartItem(Long userId) {

    }

    // 장바구니 상품 구매
    @Override
    public List<CartItem> purchaseCartItem(Long userId, PurchaseCartItemDto purchaseCartItemDto) {
        return null;
    }

    // ==========================관리자===========================

}
