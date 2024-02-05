package com.example.shoppingmallserver.dto.response.cart_wishlist;

import lombok.Getter;

/**
 * 장바구니 상품을 조회하기 위한 DTO 클래스입니다.
 * 상품 이미지 URL, 상품 이름, 상품 가격, 수량 정보를 포함합니다.
 */
@Getter
public class ReadCartItemResponseDto {
    private final Long itemId; // 상품 ID
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 가격
    private final int discountRate; // 이벤트 할인률
    private final int discountPrice; // 이벤트 할인가
    private final int quantity; // 수량

    /**
     * ItemCartInquiry와 수량을 이용하여 ReadCartItemResponseDto를 생성합니다.
     *
     * @param itemDto 장바구니 상품 정보를 담은 DTO
     * @param quantity 상품의 수량
     */
    public ReadCartItemResponseDto(ItemDto itemDto, int quantity) {
        this.itemId = itemDto.getId();
        this.image = itemDto.getThumbnailImage();
        this.name = itemDto.getName();
        this.price = itemDto.getPrice();
        this.discountRate = itemDto.getDiscountRate();
        this.discountPrice = itemDto.getDiscountPrice();
        this.quantity = quantity;
    }
}
