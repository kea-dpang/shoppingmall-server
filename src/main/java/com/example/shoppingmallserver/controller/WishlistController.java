package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;



    // 사용자 위시리스트 삭제
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
