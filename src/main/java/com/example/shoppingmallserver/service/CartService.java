package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.AddCartItemDto;
import com.example.shoppingmallserver.dto.PurchaseCartItemDto;
import com.example.shoppingmallserver.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    // 장바구니 상품 조회
    List<Cart> getCartItemByUserId(Long user_id);

    // 장바구니 상품 추가
    Cart addCartItem(Long user_id, AddCartItemDto addCartItemDto);

    // 장바구니 상품 삭제
    void deleteCartItem(Long user_id);

    // 장바구니 상품 구매
    List<Cart> purchaseCartItem(Long id, PurchaseCartItemDto purchaseCartItemDto);

    // ==========================관리자===========================

}
