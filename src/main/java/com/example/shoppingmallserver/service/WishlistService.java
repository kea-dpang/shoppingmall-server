package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.AddWishlistItemDto;
import com.example.shoppingmallserver.dto.UpdateMileageStatusDto;
import com.example.shoppingmallserver.entity.Mileage;
import com.example.shoppingmallserver.entity.Wishlist;

import java.util.List;

public interface WishlistService {

    // 위시리스트 상품 추가
    Wishlist addWishlistItem(Long user_id, AddWishlistItemDto addWishlistItemDto);

    // 위시리스트 목록 조회
    List<Wishlist> getWishlistItemList(Long user_id);

    // 위시리스트 상품 삭제
    void deleteWishlistItem(Long id);

    // ==========================관리자===========================

}
