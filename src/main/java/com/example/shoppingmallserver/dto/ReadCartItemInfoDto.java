package com.example.shoppingmallserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadCartItemInfoDto {
    private String image;
    private String name;
    private int price;
}
