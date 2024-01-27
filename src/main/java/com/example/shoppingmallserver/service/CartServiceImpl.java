package com.example.shoppingmallserver.service;


import com.example.shoppingmallserver.dto.ReadItemsDto;
import com.example.shoppingmallserver.dto.ReadItemsInfoDto;
import com.example.shoppingmallserver.entity.cart.Cart;
import com.example.shoppingmallserver.feign.ItemServiceCartItemClient;
import com.example.shoppingmallserver.repository.CartRepository;
import com.example.shoppingmallserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemServiceCartItemClient itemServiceCartItemClient;

    // 장바구니 상품 조회
    @Override
    public List<ReadItemsDto> getCartItemList(Long userId) {

        // 사용자 ID 기반으로 장바구니 조회
        Cart cart = cartRepository.findCartByUser_UserId(userId);

        // 장바구니 기반으로 아이템 목록 조회한것을 반환
        List<Long> itemIds = cart.getItemIds();

        // 아이템 ID 리스트를 이용하여 각 아이템의 상세 정보를 조회
        List<ReadItemsInfoDto> itemInfos = itemServiceCartItemClient.getItemsInfo(itemIds);

        // 아이템 정보와 장바구니 아이템의 수량을 이용하여 응답 DTO를 생성후 반환 (mapToObj -> map)요소반복으로 변경)
        // + 변수 인라인화
        return itemInfos.stream().map(itemInfo -> new ReadItemsDto(cart, itemInfo))
                .toList();
    }

    // 장바구니 상품 추가
    @Override
    public void addCartItem(Long userId, Long itemId) {

        // Cart에 상품 추가(빌더)
        Cart cart = Cart.builder()
                .itemId(itemId)
                .quantity(1)
                .build();

        // 저장
        cartRepository.save(cart);

        cartRepository.findCartByUser_UserId(userId);
    }

    // 장바구니 상품 삭제
    @Override
    public void deleteCartItem(Long userId, Long itemId) {
        cartRepository.deleteByUser_UserIdAndItemId(userId, itemId);
    }
}
