package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.cart.CartItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadCartItemDto {
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 가격
    private final int quantity; // 수량

    public ReadCartItemDto(ReadCartItemInfoDto readCartItemInfoDto, int quantity) {
        this.image = readCartItemInfoDto.getImage();
        this.name = readCartItemInfoDto.getName();
        this.price = readCartItemInfoDto.getPrice();
        this.quantity = quantity;
    }
}
