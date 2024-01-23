package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.cart.CartItem;
import lombok.Getter;

import java.util.List;

/**
 * 장바구니 상품을 조회하기 위한 DTO 클래스입니다.
 * 상품 이미지 URL, 상품 이름, 상품 가격, 수량 정보를 포함합니다.
 */
@Getter
public class ReadCartItemDto {
    private final Long cartItemId;
    private final String image; // 상품 이미지 URL
    private final String name; // 상품 이름
    private final int price; // 상품 가격
    private final int quantity; // 수량

    /**
     * ReadCartItemInfoDto와 수량을 이용하여 ReadCartItemDto를 생성합니다.
     *
     * @param cartItem 장바구니 상품 정보를 담은 엔티티
     * @param readCartItemInfoDto 장바구니 상품 정보를 담은 DTO
     */
    public ReadCartItemDto(CartItem cartItem, ReadCartItemInfoDto readCartItemInfoDto) {
        this.cartItemId = cartItem.getCartItemId();
        this.image = readCartItemInfoDto.getImage();
        this.name = readCartItemInfoDto.getName();
        this.price = readCartItemInfoDto.getPrice();
        this.quantity = cartItem.getQuantity();
    }
}
