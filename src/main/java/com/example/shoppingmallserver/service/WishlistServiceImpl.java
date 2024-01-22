package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.AddWishlistItemDto;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;
import com.example.shoppingmallserver.repository.UserRepository;
import com.example.shoppingmallserver.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;

    // 위시리스트 상품 추가
    @Override
    public WishlistItem addWishlistItem(Long userId, Long itemId) {

        // userId에 해당하는 사용자 찾아오기
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        // 새 WishlistItem 생성
        WishlistItem wishlistItem = WishlistItem.builder()
                .user(user)
                .itemId(itemId)
                .addedAt(LocalDate.now())
                .build();

        // 저장
        wishlistRepository.save(wishlistItem);

        return wishlistRepository.findWishlistItemByUserId(userId);
    }

    // 위시리스트 목록 조회
    @Override
    public List<WishlistItem> getWishlistItemByUserId(Long userId) {
        return wishlistRepository.findWishlistItemListByUserId(userId);
    }

    // 위시리스트 상품 삭제
    @Override
    public void deleteWishlistItem(Long userId, Long itemId) {
        wishlistRepository.deleteByUserIdAndWishlistItemId(userId, itemId);
    }

    // ==========================관리자===========================

}
