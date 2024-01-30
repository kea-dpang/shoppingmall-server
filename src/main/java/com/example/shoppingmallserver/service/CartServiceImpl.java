package com.example.shoppingmallserver.service;


import com.example.shoppingmallserver.dto.ReadItemsDto;
import com.example.shoppingmallserver.dto.ReadItemsInfoDto;
import com.example.shoppingmallserver.entity.cart.Cart;
import com.example.shoppingmallserver.feign.ItemFeignClient;
import com.example.shoppingmallserver.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ItemFeignClient itemFeignClient;

    // 장바구니 상품 조회
    @Override
    public List<ReadItemsDto> getCartItemList(Long userId) {

        // 사용자 ID 기반으로 장바구니 조회
        Cart cart = cartRepository.findCartByUserId(userId);

        // 장바구니 기반으로 아이템 목록 조회
        Map<Long, Integer> items = cart.getItems();

        // 아이템 ID 리스트를 이용하여 각 아이템의 상세 정보를 조회
        List<ReadItemsInfoDto> itemInfos = itemFeignClient.getItemsInfo(new ArrayList<>(items.keySet()));

        // 장바구니에 있는 각 아이템의 정보와 수량을 이용하여 ReadItemsDto 객체를 생성하고, 이를 리스트로 변환하여 반환
        return itemInfos.stream().map(itemInfo -> {
            Integer quantity = items.get(itemInfo.getItemId()); // 아이템의 수량을 조회
            return new ReadItemsDto(itemInfo, quantity); // 아이템 정보와 수량을 이용하여 ReadItemsDto 객체를 생성
        }).toList();
    }

    // 장바구니 상품 추가
    @Override
    public void addCartItem(Long userId, Long itemId) {

        // 특정 사용자의 장바구니를 찾는다.
        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart == null) { // 카트가 없을 경우
            // 새 카트 생성 (빌더)
            cart = Cart.builder().build();
        }

        // 장바구니에 아이템 추가
        cart.addItem(itemId);

        // 저장
        cartRepository.save(cart);
    }

    // 장바구니 상품 삭제
    @Override
    public void deleteCartItem(Long userId, Long itemId) {
        cartRepository.deleteByUserIdAndItemId(userId, itemId);
    }
}
