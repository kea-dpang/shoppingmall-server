package com.example.shoppingmallserver.dto;

import com.example.shoppingmallserver.entity.cart.CartItem;
import lombok.Getter;

/**
 * 장바구니에 추가할 상품 정보를 담는 DTO 클래스입니다.
 * 상품 이미지, 이름, 가격, 수량 정보를 포함합니다.
 */
@Getter
public class AddCartItemDto {
    private final Long cartItemId;
    private final String image;
    private final String name;
    private final int price;
    private final int quantity;

    /**
     * 장바구니에 추가할 상품 정보와 수량을 이용하여 AddCartItemDto를 생성합니다.
     *
     * @param cartItem 장바구니 상품 정보를 담은 엔티티
     * @param addCartItemInfoDto 장바구니에 추가할 상품 정보를 담은 DTO
     */
    public AddCartItemDto(CartItem cartItem, AddCartItemInfoDto addCartItemInfoDto) {
        this.cartItemId = cartItem.getCartItemId();
        this.image = addCartItemInfoDto.getImage();
        this.name = addCartItemInfoDto.getName();
        this.price = addCartItemInfoDto.getPrice();
        this.quantity = cartItem.getQuantity()+1; // 아이템 추가이므로 +1한 값을 전달
}
