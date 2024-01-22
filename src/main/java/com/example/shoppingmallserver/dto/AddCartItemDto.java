package com.example.shoppingmallserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AddCartItemDto {
    private final String image;
    private final String name;
    private final int price;
    private final int quantity;

    public AddCartItemDto(AddCartItemInfoDto addCartItemInfoDto, int quantity) {
        this.image = addCartItemInfoDto.getImage();
        this.name = addCartItemInfoDto.getName();
        this.price = addCartItemInfoDto.getPrice();
        this.quantity = quantity;
    }
}
