package com.example.shoppingmallserver.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 장바구니 상품을 조회하기 위한 DTO 클래스입니다.
 * 상품 이미지, 이름, 가격 정보를 포함합니다.
 * 이 DTO는 주로 item-server에 요청을 보낼 때 사용됩니다.
 */
@Getter
@Setter
public class ReadCartItemInfoDto {
    private String image;
    private String name;
    private int price;
}
