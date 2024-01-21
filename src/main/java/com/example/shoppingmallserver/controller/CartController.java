package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.ReadCartItemDto;
import com.example.shoppingmallserver.dto.ReadCartItemInfoDto;
import com.example.shoppingmallserver.entity.cart.CartItem;
import com.example.shoppingmallserver.feign.ItemServiceCartItemClient;
import com.example.shoppingmallserver.service.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemServiceCartItemClient itemServiceCartItemClient;

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<List<ReadCartItemDto>>> getCartItemList(@PathVariable Long userId) {

        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);

        List<Long> itemIds = cartItems.stream()
                .map(CartItem::getItemId)
                .collect(Collectors.toList());

        List<ReadCartItemInfoDto> itemInfos = itemServiceCartItemClient.getCartItemInfo(itemIds);

        List<ReadCartItemDto> data = IntStream.range(0, itemInfos.size())
                .mapToObj(i -> new ReadCartItemDto(itemInfos.get(i), cartItems.get(i).getQuantity()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자의 장바구니 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }
}
