package com.example.shoppingmallserver.feign;

import com.example.shoppingmallserver.dto.AddCartItemInfoDto;
import com.example.shoppingmallserver.dto.ReadCartItemInfoDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "item-service", url = "${feign.client.item-service.url}")
public interface ItemServiceCartItemClient {
  
    @GetMapping("/items/cart")
    List<ReadCartItemInfoDto> getCartItemInfo(@RequestParam("itemIds") List<Long> itemIds);
    // 상품 ID 목록을 쿼리 파라미터로 전달
  
    @GetMapping("/items/cart/{itemId}")
    AddCartItemInfoDto getItem(@PathVariable("itemId") Long itemId);
    // 추가할 상품 ID를 파라미터로 전달
  
}
