package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.cart_wishlist.ItemCartInquiryDto;
import com.example.shoppingmallserver.entity.wishlist.Wishlist;
import com.example.shoppingmallserver.feign.item.ItemFeignClient;
import com.example.shoppingmallserver.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemFeignClient itemFeignClient;

    /**
     * 사용자의 위시리스트 목록을 조회합니다.
     *
     * @param userId 위시리스트를 조회할 사용자의 ID
     * @return 사용자의 위시리스트. 위시리스트에 포함된 아이템들이 반환됩니다.
     */
    @Override
    public List<ItemCartInquiryDto> getWishlistItemList(Long userId) {

        // 사용자 ID를 기반으로 위시리스트 조회
        Wishlist wishlist = wishlistRepository.findWishlistByUserId(userId);

        // 장바구니를 기반으로 위시리스트 아이템 목록을 조회
        List<Long> itemIds = wishlist.getItemIds();

        // 아이템 ID 리스트를 이용하여 각 아이템의 상세 정보를 조회
        List<ItemCartInquiryDto> itemInfos = itemFeignClient.getItemsInfo(itemIds).getBody().getData();

        log.info("위시리스트 상품 조회 성공. 사용자 아이디: {}", userId);

        // 아이템 정보를 이용하여 응답 DTO를 생성후 반환 (mapToObj -> map)요소반복으로 변경)
        // + 변수 인라인화
        return itemInfos.stream()
                .map(itemInfo -> new ItemCartInquiryDto(
                        itemInfo.getItemId(),
                        itemInfo.getImage(),
                        itemInfo.getName(),
                        itemInfo.getPrice(),
                        itemInfo.getDiscountRate(),
                        itemInfo.getDiscountPrice()))
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

        // 사용자 ID 기반으로 위시리스트 찾기
        Wishlist wishlist = wishlistRepository.findWishlistByUserId(userId);

        if(wishlist == null) { // 위시리스트가 없을 경우
            // 새 위시리스트 생성(빌더)
            wishlist = Wishlist.builder().build();
        }

        // 위시리스트에 아이템 추가
        wishlist.addItem(itemId);

        // 저장
        wishlistRepository.save(wishlist);

        log.info("위시리스트 상품 추가 성공. 사용자 아이디: {}, 상품 아이디: {}", userId, itemId);
    }

    /**
     * 사용자의 위시리스트에서 상품을 삭제합니다.
     *
     * @param userId 위시리스트에서 상품을 삭제할 사용자의 ID
     * @param itemId 위시리스트에서 삭제할 상품의 ID
     */
    @Override
    public void deleteWishlistItem(Long userId, Long itemId) {
        // 사용자 ID로 위시리스트를 조회합니다.
        Wishlist wishlist = wishlistRepository.findWishlistByUserId(userId);

        // 장바구니에서 아이템을 삭제합니다.
        wishlist.removeItem(itemId);

        // 변경사항을 저장합니다.
        wishlistRepository.save(wishlist);

        log.info("위시리스트 상품 삭제 성공. 사용자 아이디: {}, 상품 아이디: {}", userId, itemId);
    }
}
