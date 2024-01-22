package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.cart.CartItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.List;

/**
 * 장바구니 상품을 조회하기 위한 DTO 클래스입니다.
 * 상품 이미지 URL, 상품 이름, 상품 가격, 수량 정보를 포함합니다.
 */
@Getter
public class ReadCartItemDto {
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 가격
    private final int quantity; // 수량

    /**
     * ReadCartItemInfoDto와 수량을 이용하여 ReadCartItemDto를 생성합니다.
     *
     * @param readCartItemInfoDto 장바구니 상품 정보를 담은 DTO
     * @param quantity 상품 수량
     */
    public ReadCartItemDto(ReadCartItemInfoDto readCartItemInfoDto, int quantity) {
        this.image = readCartItemInfoDto.getImage();
        this.name = readCartItemInfoDto.getName();
        this.price = readCartItemInfoDto.getPrice();
        this.quantity = quantity;
    }
}
