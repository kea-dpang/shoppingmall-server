package com.example.shoppingmallserver.dto.response.cart_wishlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id; // 상품 ID
    private String thumbnailImage; // 상품 이미지 URL
    private String name; // 상품 이름
    private int price; // 상품 정가
    private int quantity; // 상품 재고 수량(사용하지 않음)
    private int discountRate; // 할인율
    private int discountPrice; // 상품 판매가
}
