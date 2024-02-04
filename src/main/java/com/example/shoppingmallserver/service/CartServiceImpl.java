package com.example.shoppingmallserver.service;


import com.example.shoppingmallserver.dto.response.cart_wishlist.ReadCartItemResponseDto;
import com.example.shoppingmallserver.dto.response.cart_wishlist.ItemCartInquiryResponseDto;
import com.example.shoppingmallserver.entity.cart.Cart;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.exception.ItemNotInCartException;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.feign.item.ItemFeignClient;
import com.example.shoppingmallserver.repository.CartRepository;

import com.example.shoppingmallserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ItemFeignClient itemFeignClient;

    // 장바구니 상품 조회
    @Override
    public List<ReadCartItemResponseDto> getCartItemList(Long userId) {

        // 없는 사용자의 장바구니를 조회할 경우 예외 발생
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        // 사용자 ID 기반으로 장바구니 조회
        Cart cart = cartRepository.findCartByUserId(userId);

        // 장바구니가 비어있으면 빈 리스트 반환
        if (cart == null) {
            // 새 카트 생성 (빌더)
            cart = Cart.builder().build();
        }

        // 장바구니 기반으로 아이템 목록 조회
        Map<Long, Integer> items = cart.getItems();

        // 아이템 ID 리스트를 이용하여 각 아이템의 상세 정보를 조회
        List<ItemCartInquiryResponseDto> itemInfos = itemFeignClient.getItemsInfo(new ArrayList<>(items.keySet())).getBody().getData();

        log.info("장바구니 상품 조회 성공. 사용자 아이디: {}", userId);

        // 장바구니에 있는 각 아이템의 정보와 수량을 이용하여 ReadItemsDto 객체를 생성하고, 이를 리스트로 변환하여 반환
        return itemInfos.stream().map(itemInfo -> {
            Integer quantity = items.get(itemInfo.getItemId()); // 아이템의 수량을 조회
            return new ReadCartItemResponseDto(itemInfo, quantity); // 아이템 정보와 수량을 이용하여 ReadItemsDto 객체를 생성
        }).toList();
    }

    // 장바구니 상품 추가
    @Override
    public void addCartItem(Long userId, Long itemId, int quantity) {

        // 없는 사용자의 장바구니를 조회할 경우 예외 발생
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        // 특정 사용자의 장바구니를 찾는다.
        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart == null) { // 카트가 없을 경우
            // 새 카트 생성 (빌더)
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            cart = Cart.builder().user(user).build();
        }

        // 장바구니에 아이템 추가
        cart.addItem(itemId, quantity);

        // 저장
        cartRepository.save(cart);

        log.info("장바구니 상품 추가 성공. 사용자 아이디: {}, 상품 아이디: {}", userId, itemId);
    }

    /**
     * 사용자의 장바구니에서 상품을 삭제합니다.
     *
     * @param userId 아이템을 삭제할 장바구니를 가진 사용자의 ID
     * @param itemId 삭제할 아이템의 ID
     */
    @Override
    public void deleteCartItem(Long userId, Long itemId) {

        // 없는 사용자의 장바구니를 조회할 경우 예외 발생
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        
        // 사용자 ID로 장바구니를 조회합니다.
        Cart cart = cartRepository.findCartByUserId(userId);

        // 장바구니에서 아이템을 삭제합니다.
        cart.removeItem(itemId);

        // 변경사항을 저장합니다.
        cartRepository.save(cart);

        log.info("장바구니 상품 삭제 성공. 사용자 아이디: {}, 상품 아이디: {}", userId, itemId);
    }

    @Override
    public void minusCartItem(Long userId, Long itemId) {

        // 없는 사용자의 장바구니를 조회할 경우 예외 발생
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        // 특정 사용자의 장바구니를 찾는다.
        Cart cart = cartRepository.findCartByUserId(userId);

        // 장바구니에 아이템 갯수 -1
        cart.addItem(itemId, -1); // 개수에 -1을 더하면 한개 빼게 되는 로직임

        // 저장
        cartRepository.save(cart);

        log.info("장바구니 상품 갯수 감소 성공. 사용자 아이디: {}, 상품 아이디: {}, 상품의 개수 : {} -> {}", userId, itemId, cart.getItems().get(itemId), cart.getItems().get(itemId) - 1);
    }
}
