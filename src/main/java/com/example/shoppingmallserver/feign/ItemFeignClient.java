package com.example.shoppingmallserver.feign;

import com.example.shoppingmallserver.dto.ReadItemsInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// 자바독 주석 나중에 추가
@FeignClient(name = "item-server")
public interface ItemFeignClient {

    // 장바구니 목록 조회를 위한 정보 요청
    // 위시리스트 상품 조회를 위한 정보 요청
    @GetMapping("/list")
    List<ReadItemsInfoDto> getItemsInfo(@RequestBody List<Long> itemIds);
}
