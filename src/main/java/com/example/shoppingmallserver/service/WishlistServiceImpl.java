package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.AddWishlistItemDto;
import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;
import com.example.shoppingmallserver.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    // 위시리스트 상품 추가
    @Override
    public WishlistItem addWishlistItem(Long userId, AddWishlistItemDto addWishlistItemDto) {
        return null;
    }

    // 위시리스트 목록 조회
    @Override
    public List<WishlistItem> getWishlistItemList(Long userId) {
        return null;
    }

    // 위시리스트 상품 삭제
    @Override
    public void deleteWishlistItem(Long userId, Long itemId) {
        wishlistRepository.deleteByUserIdAndWishlistItemId(userId, itemId);
    }

    // ==========================관리자===========================

}
