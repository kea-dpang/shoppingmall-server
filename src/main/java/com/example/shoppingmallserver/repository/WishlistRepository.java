package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findWishlistItemListByUserId(Long userId);

    void deleteByUserIdAndWishlistItemId(Long userId, Long wishlistItemId);
}
