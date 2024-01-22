package com.example.shoppingmallserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemInfoDto {
    private String image;
    private String name;
    private int price;
}