package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.response.cart_wishlist.ReadCartItemResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    // 장바구니 상품 조회
    List<ReadCartItemResponseDto> getCartItemList(Long userId);

    // 장바구니 상품 추가
    void addCartItem(Long userId, Long itemId, int quantity);

    // 장바구니 상품 삭제
    void deleteCartItem(Long userId, Long itemId);

}
