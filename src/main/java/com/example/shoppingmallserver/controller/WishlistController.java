package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.cart.CartItem;
import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;
import com.example.shoppingmallserver.feign.ItemServiceCartItemClient;
import com.example.shoppingmallserver.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController("/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final ItemServiceCartItemClient itemServiceCartItemClient;

    // 위시리스트 상품 추가
    @PostMapping("/{userId}/{itemId}")
    public ResponseEntity<SuccessResponse<AddWishlistItemDto>> addWishlistItem(@PathVariable Long userId, Long itemId) {

        // 아이템 서버에서 받아온 아이템 정보 반환
        AddWishlistItemDto itemInfo = itemServiceCartItemClient.getWishlistItemsInfo(itemId);

        // 상품 정보와 사용자 아이디를 통해 상품 추가
        wishlistService.addWishlistItem(userId, itemId);

        // 아이템 정보로 데이터 구성
        AddWishlistItemDto data = new AddWishlistItemDto(itemInfo);

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "장바구니에 상품을 성공적으로 추가하였습니다.", data),
                HttpStatus.OK
        );
    }

    // 위시리스트 상품 조회
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<List<ReadWishlistItemDto>>> addWishlistItem(@PathVariable Long userId) {

        // 사용자 ID를 기반으로 위시리스트 아이템 목록을 조회
        List<WishlistItem> wishlistItems = wishlistService.getWishlistItemByUserId(userId);

        // 조회한 위시리스트 아이템 목록에서 아이템 ID만을 추출하여 새로운 리스트를 생성
        List<Long> itemIds = wishlistItems.stream()
                .map(WishlistItem::getItemId)
                .toList();

        // 아이템 ID 리스트를 이용하여 각 아이템의 상세 정보를 조회
        List<ReadWishlistItemDto> itemInfos = itemServiceCartItemClient.getWishlistItemsInfo(itemIds);

        // 아이템 정보를 이용하여 응답 DTO를 생성
        List<ReadWishlistItemDto> data = IntStream.range(0, itemInfos.size())
                .mapToObj(i -> new ReadWishlistItemDto(itemInfos.get(i)))
                .toList();

        // 생성한 응답 DTO를 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 장바구니 정보가 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자의 장바구니 정보를 성공적으로 조회하였습니다.", data),
                HttpStatus.OK
        );
    }


    // 위시리스트 상품 삭제
    @DeleteMapping("/{userId}/{wishlistItemId}")
    public ResponseEntity<SuccessResponse<Void>> deleteWishlistItem(@PathVariable Long userId, @PathVariable Long wishlistItemId) {

        // 사용자 아이디와 아이템 정보를 통해 삭제
        wishlistService.deleteWishlistItem(userId, wishlistItemId);

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "위시리스트에서 상품을 성공적으로 삭제하였습니다.", null),
                HttpStatus.OK
        );
    }

}
