package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.entity.wishlist.Wishlist;
import com.example.shoppingmallserver.repository.UserRepository;
import com.example.shoppingmallserver.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;

    /**
     * 사용자의 위시리스트 목록을 조회합니다.
     *
     * @param userId 위시리스트를 조회할 사용자의 ID
     * @return 사용자의 위시리스트. 위시리스트에 포함된 아이템들이 반환됩니다.
     */
    @Override
    public Wishlist getWishlistItemList(Long userId) {
        return wishlistRepository.findWishlistByUser_UserId(userId);
    }

    /**
     * 사용자의 위시리스트에 상품을 추가합니다.
     *
     * @param userId 위시리스트에 상품을 추가할 사용자의 ID
     * @param itemId 위시리스트에 추가할 상품의 ID
     */
    @Override
    public void addWishlistItem(Long userId, Long itemId) {

        // Wishlist에 상품 추가(빌더)
        Wishlist wishlist = Wishlist.builder()
                .itemId(itemId)
                .build();

        // 저장
        wishlistRepository.save(wishlist);

        wishlistRepository.findWishlistByUser_UserId(userId);
    }

    /**
     * 사용자의 위시리스트에서 상품을 삭제합니다.
     *
     * @param userId 위시리스트에서 상품을 삭제할 사용자의 ID
     * @param itemId 위시리스트에서 삭제할 상품의 ID
     */
    @Override
    public void deleteWishlistItem(Long userId, Long itemId) {
        wishlistRepository.deleteByUser_UserIdAndItemId(userId, itemId);
    }
}
