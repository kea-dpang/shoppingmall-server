package com.example.shoppingmallserver.feign;

import com.example.shoppingmallserver.dto.AddCartItemInfoDto;
import com.example.shoppingmallserver.dto.ReadCartItemInfoDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// 자바독 주석 나중에 추가
@FeignClient(name = "item-service", url = "${feign.client.item-service.url}")
public interface ItemServiceCartItemClient {

    // 장바구니 목록 조회를 위한 정보 요청
    @GetMapping("/items/cart")
    List<ReadCartItemInfoDto> getCartItemInfo(@RequestParam("itemIds") List<Long> itemIds);
    // 상품 ID 목록을 쿼리 파라미터로 전달

    // 장바구니 상품 추가를 위한 정보 요청
    @GetMapping("/items/cart/{itemId}")
    AddCartItemInfoDto getItem(@PathVariable("itemId") Long itemId);
    // 추가할 상품 ID를 파라미터로 전달
  
}
