package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.ReadItemsInfoDto;
import com.example.shoppingmallserver.entity.wishlist.Wishlist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {

    /**
     * 사용자의 위시리스트 목록을 조회합니다.
     *
     * @param userId 위시리스트를 조회할 사용자의 ID
     * @return 사용자의 위시리스트. 위시리스트에 포함된 아이템들이 반환됩니다.
     */
    List<ReadItemsInfoDto> getWishlistItemList(Long userId);

    /**
     * 사용자의 위시리스트에 상품을 추가합니다.
     *
     * @param userId 위시리스트에 상품을 추가할 사용자의 ID
     * @param itemId 위시리스트에 추가할 상품의 ID
     */
    void addWishlistItem(Long userId, Long itemId);

    /**
     * 사용자의 위시리스트에서 상품을 삭제합니다.
     *
     * @param userId 위시리스트에서 상품을 삭제할 사용자의 ID
     * @param itemId 위시리스트에서 삭제할 상품의 ID
     */
    void deleteWishlistItem(Long userId, Long itemId);
}
