package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {


    void deleteByUserIdAndWishlistItemId(Long userId, Long wishlistItemId);
}
