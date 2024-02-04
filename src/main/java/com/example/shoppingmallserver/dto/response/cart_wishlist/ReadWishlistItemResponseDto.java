package com.example.shoppingmallserver.dto.response.cart_wishlist;

import lombok.Getter;

@Getter
public class ReadWishlistItemResponseDto {
    private final Long itemId; // 상품 ID
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 정가
    private final int discountRate; // 할인율
    private final int discountPrice; // 상품 판매가

    public ReadWishlistItemResponseDto(ReadWishlistItemResponseDto readWishlistItemResponseDto) {
        this.itemId = readWishlistItemResponseDto.getItemId();
        this.image = readWishlistItemResponseDto.getImage();
        this.name = readWishlistItemResponseDto.getName();
        this.price = readWishlistItemResponseDto.getPrice();
        this.discountRate = readWishlistItemResponseDto.getDiscountRate();
        this.discountPrice = readWishlistItemResponseDto.getDiscountPrice();
    }
}
