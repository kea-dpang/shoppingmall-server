package com.example.shoppingmallserver.feign.item;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.response.cart_wishlist.ItemCartInquiryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 자바독 주석 나중에 추가
@FeignClient(name = "item-server")
public interface ItemFeignClient {

    // 장바구니 목록 조회를 위한 정보 요청
    // 위시리스트 상품 조회를 위한 정보 요청
    @GetMapping("/api/items/cart/inquiryItem")
    ResponseEntity<SuccessResponse<List<ItemCartInquiryResponseDto>>> getItemsInfo(@RequestParam List<Long> itemIds);
}
