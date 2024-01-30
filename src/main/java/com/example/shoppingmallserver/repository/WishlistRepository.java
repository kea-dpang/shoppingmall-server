package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.wishlist.Wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * 사용자 ID로 위시리스트를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 조회된 위시리스트
     */
    Wishlist findWishlistByUserId(Long userId);
}
