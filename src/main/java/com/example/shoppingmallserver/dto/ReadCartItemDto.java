package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.cart.CartItem;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadCartItemDto {
    private final String itemInfo;  // 상품 정보를 JSON 문자열로 저장 -> 상품 이미지, 상품명, 가격 3가지
    private final int quantity; // 수량

    public ReadCartItemDto(List<CartItem> cartItems) {

    }
}
