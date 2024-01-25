package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.BaseResponse;
import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.*;
import com.example.shoppingmallserver.entity.wishlist.Wishlist;
import com.example.shoppingmallserver.feign.ItemServiceCartItemClient;
import com.example.shoppingmallserver.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 위시리스트 상품 정보를 관리하는 Controller 클래스입니다.
 * 위시리스트 상품 조회, 추가, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/wishlists/{userId}")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final ItemServiceCartItemClient itemServiceCartItemClient;

    /**
     * 사용자의 위시리스트의 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 성공 응답 메시지와 함께 위시리스트 내용을 담은 DTO를 반환
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<List<ReadWishlistItemDto>>> getWishlistItemList(@PathVariable Long userId) {

        // 사용자 ID를 기반으로 위시리스트 조회
        Wishlist wishlist = wishlistService.getWishlistItemList(userId);

        // 장바구니를 기반으로 위시리스트 아이템 목록을 조회
        List<Long> itemIds = wishlist.getItemIds();

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

    /**
     * 사용자의 위시리스트에 아이템을 추가합니다.
     *
     * @param userId 사용자 ID
     * @param itemId 추가할 아이템의 ID
     * @return 성공 메시지와 함께 HTTP 상태 코드 201(CREATED)를 반환
     */
    @PostMapping("/{itemId}")
    public ResponseEntity<BaseResponse> addWishlistItem(@PathVariable Long userId, Long itemId) {

        // 상품 정보와 사용자 아이디를 통해 위시리스트에 상품 추가
        wishlistService.addWishlistItem(userId, itemId);

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "장바구니에 상품을 성공적으로 추가하였습니다."),
                HttpStatus.CREATED
        );
    }

    /**
     * 사용자의 위시리스트에서 아이템에 해당하는 위시리스트 항목을 삭제합니다.
     *
     * @param userId 사용자 ID
     * @param itemId 삭제할 장바구니 항목 ID
     * @return 성공 메시지와 함께 HTTP 상태 코드 204(NO_CONTENT)를 반환
     */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<SuccessResponse<Void>> deleteWishlistItem(@PathVariable Long userId, @PathVariable Long itemId) {

        // 사용자 아이디와 아이템 정보를 통해 삭제
        wishlistService.deleteWishlistItem(userId, itemId);

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.NO_CONTENT.value(), "위시리스트에서 상품을 성공적으로 삭제하였습니다.", null),
                HttpStatus.NO_CONTENT
        );
    }

}
