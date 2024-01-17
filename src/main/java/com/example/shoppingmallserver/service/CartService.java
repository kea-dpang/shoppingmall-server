package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.AddCartItemDto;
import com.example.shoppingmallserver.dto.PurchaseCartItemDto;
import com.example.shoppingmallserver.entity.cart.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    // 장바구니 상품 조회
    List<CartItem> getCartItemByUserId(Long user_id);

    // 장바구니 상품 추가
    CartItem addCartItem(Long user_id, AddCartItemDto addCartItemDto);

    // 장바구니 상품 삭제
    void deleteCartItem(Long user_id);

    // 장바구니 상품 구매
    List<CartItem> purchaseCartItem(Long id, PurchaseCartItemDto purchaseCartItemDto);

    // ==========================관리자===========================

}
