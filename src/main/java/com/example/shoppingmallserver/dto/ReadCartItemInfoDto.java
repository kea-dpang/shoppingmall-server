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
    private Long itemId; // 상품 ID
    private String image; // 상품 이미지 URL
    private String name; // 상품 이름
    private int price; // 상품 정가
    private int discountRate; // 할인율
    private int discountPrice; // 상품 판매가
}
