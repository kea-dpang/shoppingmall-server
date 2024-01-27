package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.cart.Cart;
import lombok.Getter;

/**
 * 장바구니 상품을 조회하기 위한 DTO 클래스입니다.
 * 상품 이미지 URL, 상품 이름, 상품 가격, 수량 정보를 포함합니다.
 */
@Getter
public class ReadItemsDto {
    private final Long itemId; // 상품 ID
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 가격
    private final int discountRate; // 이벤트 할인률
    private final int discountPrice; // 이벤트 할인가
    private final int quantity; // 수량

    /**
     * ReadCartItemInfoDto와 수량을 이용하여 ReadCartItemDto를 생성합니다.
     *
     * @param cart 장바구니 상품 정보를 담은 엔티티
     * @param readItemsInfoDto 장바구니 상품 정보를 담은 DTO
     */
    public ReadItemsDto(Cart cart, ReadItemsInfoDto readItemsInfoDto) {
        this.itemId = readItemsInfoDto.getItemId();
        this.image = readItemsInfoDto.getImage();
        this.name = readItemsInfoDto.getName();
        this.price = readItemsInfoDto.getPrice();
        this.discountRate = readItemsInfoDto.getDiscountRate();
        this.discountPrice = readItemsInfoDto.getDiscountPrice();
        this.quantity = cart.getQuantity();
    }
}
