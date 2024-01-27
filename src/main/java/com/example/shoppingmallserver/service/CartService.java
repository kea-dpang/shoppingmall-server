package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.ReadItemsDto;
import com.example.shoppingmallserver.entity.cart.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    // 장바구니 상품 조회
    List<ReadItemsDto> getCartItemList(Long userId);

    // 장바구니 상품 추가
    void addCartItem(Long userId, Long itemId);

    // 장바구니 상품 삭제
    void deleteCartItem(Long userId, Long itemId);

}
