package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.ReadItemsInfoDto;
import com.example.shoppingmallserver.entity.wishlist.Wishlist;
import com.example.shoppingmallserver.feign.ItemServiceCartItemClient;
import com.example.shoppingmallserver.repository.UserRepository;
import com.example.shoppingmallserver.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemServiceCartItemClient itemServiceCartItemClient;

    /**
     * 사용자의 위시리스트 목록을 조회합니다.
     *
     * @param userId 위시리스트를 조회할 사용자의 ID
     * @return 사용자의 위시리스트. 위시리스트에 포함된 아이템들이 반환됩니다.
     */
    @Override
    public List<ReadItemsInfoDto> getWishlistItemList(Long userId) {

        // 사용자 ID를 기반으로 위시리스트 조회
        Wishlist wishlist = wishlistRepository.findWishlistByUserId(userId);

        // 장바구니를 기반으로 위시리스트 아이템 목록을 조회
        List<Long> itemIds = wishlist.getItemIds();

        // 아이템 ID 리스트를 이용하여 각 아이템의 상세 정보를 조회
        List<ReadItemsInfoDto> itemInfos = itemServiceCartItemClient.getItemsInfo(itemIds);

        // 아이템 정보를 이용하여 응답 DTO를 생성후 반환 (mapToObj -> map)요소반복으로 변경)
        // + 변수 인라인화
        return itemInfos.stream().map(ReadItemsInfoDto::new)
                .toList();
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

        wishlistRepository.findWishlistByUserId(userId);
    }

    /**
     * 사용자의 위시리스트에서 상품을 삭제합니다.
     *
     * @param userId 위시리스트에서 상품을 삭제할 사용자의 ID
     * @param itemId 위시리스트에서 삭제할 상품의 ID
     */
    @Override
    public void deleteWishlistItem(Long userId, Long itemId) {
        wishlistRepository.deleteByUserIdAndItemId(userId, itemId);
    }
}
