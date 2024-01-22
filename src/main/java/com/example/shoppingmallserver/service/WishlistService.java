package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.AddWishlistItemDto;
import com.example.shoppingmallserver.dto.UpdateMileageStatusDto;
import com.example.shoppingmallserver.entity.mileage.Mileage;
import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;
import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {

    // 위시리스트 상품 추가
    WishlistItem addWishlistItem(Long userId, AddWishlistItemDto addWishlistItemDto);

    // 위시리스트 목록 조회
    List<WishlistItem> getWishlistItemByUserId(Long userId);

    // 위시리스트 상품 삭제
    void deleteWishlistItem(Long userId, Long itemId);

    // ==========================관리자===========================

}
