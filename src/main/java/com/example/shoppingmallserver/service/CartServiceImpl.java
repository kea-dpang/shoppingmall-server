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
    public List<CartItem> getCartItemByUserId(Long user_id) {
        return null;
    }

    // 장바구니 상품 추가
    @Override
    public CartItem addCartItem(Long user_id, AddCartItemDto addCartItemDto) {
        return null;
    }

    // 장바구니 상품 삭제
    @Override
    public void deleteCartItem(Long user_id) {

    }

    // 장바구니 상품 구매
    @Override
    public List<CartItem> purchaseCartItem(Long id, PurchaseCartItemDto purchaseCartItemDto) {
        return null;
    }

    // ==========================관리자===========================

}
