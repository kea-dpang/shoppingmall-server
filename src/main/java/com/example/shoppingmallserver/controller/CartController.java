package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.AddCartItemDto;
import com.example.shoppingmallserver.dto.AddCartItemInfoDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemServiceCartItemClient itemServiceCartItemClient;

    // 장바구니 아이템 조회
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
  
    // 장바구니 아이템 추가
    @PostMapping("/{userId}/{itemId}")
    public ResponseEntity<SuccessResponse<AddCartItemDto>> addCartItem(@PathVariable Long userId, Long itemId) {

        AddCartItemInfoDto itemInfo = itemServiceCartItemClient.getItem(itemId);

        CartItem cartItem = cartService.addCartItem(userId, itemInfo);

        AddCartItemDto data = new AddCartItemDto(itemInfo, cartItem.getQuantity());

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "장바구니에 상품을 성공적으로 추가하였습니다.", data),
                HttpStatus.OK
        );
    }
}
