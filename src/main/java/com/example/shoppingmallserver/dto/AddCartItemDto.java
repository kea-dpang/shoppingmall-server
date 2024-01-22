package com.example.shoppingmallserver.dto;

import lombok.Getter;

/**
 * 장바구니에 추가할 상품 정보를 담는 DTO 클래스입니다.
 * 상품 이미지, 이름, 가격, 수량 정보를 포함합니다.
 */
@Getter
public class AddCartItemDto {
    private final String image;
    private final String name;
    private final int price;
    private final int quantity;

    /**
     * 장바구니에 추가할 상품 정보와 수량을 이용하여 AddCartItemDto를 생성합니다.
     *
     * @param addCartItemInfoDto 장바구니에 추가할 상품 정보를 담은 DTO
     * @param quantity 상품 수량
     */
    public AddCartItemDto(AddCartItemInfoDto addCartItemInfoDto, int quantity) {
        this.image = addCartItemInfoDto.getImage();
        this.name = addCartItemInfoDto.getName();
        this.price = addCartItemInfoDto.getPrice();
        this.quantity = quantity;
    }
}
