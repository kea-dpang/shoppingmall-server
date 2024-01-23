package com.example.shoppingmallserver.dto;

import lombok.Getter;

@Getter
public class ReadWishlistItemInfoDto {
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 정가
    private final int discountRate; // 할인율
    private final int discountPrice; // 상품 판매가

    public ReadWishlistItemInfoDto(ReadWishlistItemDto readWishlistItemDto) {
        this.image = readWishlistItemDto.getImage();
        this.name = readWishlistItemDto.getName();
        this.price = readWishlistItemDto.getPrice();
        this.discountRate = readWishlistItemDto.getDiscountRate();
        this.discountPrice = readWishlistItemDto.getDiscountPrice();
    }
}
