package com.example.shoppingmallserver.dto.cart_wishlist;

import lombok.Getter;

@Getter
public class ItemCartInquiryDto {
    private final Long itemId; // 상품 ID
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 정가
    private final int discountRate; // 할인율
    private final int discountPrice; // 상품 판매가

    public ItemCartInquiryDto(ItemCartInquiryDto itemCartInquiryDto) {
        this.itemId = itemCartInquiryDto.getItemId();
        this.image = itemCartInquiryDto.getImage();
        this.name = itemCartInquiryDto.getName();
        this.price = itemCartInquiryDto.getPrice();
        this.discountRate = itemCartInquiryDto.getDiscountRate();
        this.discountPrice = itemCartInquiryDto.getDiscountPrice();
    }
}
