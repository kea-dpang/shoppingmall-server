package com.example.shoppingmallserver.dto.cart_wishlist;

import lombok.Getter;

@Getter
public class ReadItemsInfoDto {
    private final Long itemId; // 상품 ID
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 정가
    private final int discountRate; // 할인율
    private final int discountPrice; // 상품 판매가

    public ReadItemsInfoDto(ReadItemsInfoDto readItemsInfoDto) {
        this.itemId = readItemsInfoDto.getItemId();
        this.image = readItemsInfoDto.getImage();
        this.name = readItemsInfoDto.getName();
        this.price = readItemsInfoDto.getPrice();
        this.discountRate = readItemsInfoDto.getDiscountRate();
        this.discountPrice = readItemsInfoDto.getDiscountPrice();
    }
}
