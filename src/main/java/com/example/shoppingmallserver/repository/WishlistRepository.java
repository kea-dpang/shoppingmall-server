package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.wishlist.Wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * 사용자 ID로 위시리스트를 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return 조회된 위시리스트
     */
    Wishlist findWishlistByUserId(Long userId);

    /**
     * 사용자 ID와 위시리스트 아이템 ID에 해당하는 항목을 삭제합니다.
     *
     * @param userId 사용자 ID. 이 ID에 해당하는 사용자의 위시리스트에서 아이템을 삭제합니다.
     * @param wishlistItemId 위시리스트 아이템 ID. 이 ID에 해당하는 아이템을 위시리스트에서 삭제합니다.
     */
    void deleteByUserIdAndWishlistItemId(Long userId, Long wishlistItemId);
}
