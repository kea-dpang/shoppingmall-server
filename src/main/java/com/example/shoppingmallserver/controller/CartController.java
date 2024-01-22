package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.AddCartItemDto;
import com.example.shoppingmallserver.dto.AddCartItemInfoDto;
import com.example.shoppingmallserver.dto.ReadCartItemDto;
import com.example.shoppingmallserver.dto.ReadCartItemInfoDto;
import com.example.shoppingmallserver.entity.cart.CartItem;
import com.example.shoppingmallserver.feign.ItemServiceCartItemClient;
import com.example.shoppingmallserver.service.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@RestController("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemServiceCartItemClient itemServiceCartItemClient;

    /**
     * 사용자의 장바구니 내용을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 성공 응답 메시지와 함께 장바구니 내용을 담은 DTO를 반환
     */
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<List<ReadCartItemDto>>> getCartItemList(@PathVariable Long userId) {

        // 사용자 ID를 기반으로 장바구니 아이템 목록을 조회
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);

        // 조회한 장바구니 아이템 목록에서 아이템 ID만을 추출하여 새로운 리스트를 생성
        List<Long> itemIds = cartItems.stream()
                .map(CartItem::getItemId)
                .toList();

        // 아이템 ID 리스트를 이용하여 각 아이템의 상세 정보를 조회
        List<ReadCartItemInfoDto> itemInfos = itemServiceCartItemClient.getCartItemInfo(itemIds);

        // 아이템 정보와 장바구니 아이템의 수량을 이용하여 응답 DTO를 생성
        List<ReadCartItemDto> data = IntStream.range(0, itemInfos.size())
                .mapToObj(i -> new ReadCartItemDto(itemInfos.get(i), cartItems.get(i).getQuantity()))
                .toList();

        // 생성한 응답 DTO를 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 장바구니 정보가 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자의 장바구니 정보를 성공적으로 조회하였습니다.", data),
                
          HttpStatus.OK
        );
    }

    /**
     * 사용자의 장바구니에 아이템을 추가합니다.
     *
     * @param userId 사용자 ID
     * @param itemId 추가할 아이템의 ID
     * @return 성공 응답 메시지와 함께 추가된 아이템 정보를 담은 DTO를 반환
     */
    @PostMapping("/{userId}/{itemId}")
    public ResponseEntity<SuccessResponse<AddCartItemDto>> addCartItem(@PathVariable Long userId, Long itemId) {

        // 아이템 서버에서 받아온 아이템 정보 반환
        AddCartItemInfoDto itemInfo = itemServiceCartItemClient.getItemInfo(itemId);

        // 아이템 정보와 사용자 아이디를 통해 카트 아이템 불러오기
        CartItem cartItem = cartService.addCartItem(userId, itemId);

        // 아이템 정보와 수량으로 데이터 구성
        AddCartItemDto data = new AddCartItemDto(itemInfo, cartItem.getQuantity());

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "장바구니에 상품을 성공적으로 추가하였습니다.", data),
                HttpStatus.OK
        );
    }

    /**
     * 사용자의 장바구니에서 한 개의 장바구니 항목을 삭제합니다.
     *
     * @param userId 사용자 ID
     * @param cartItemId 삭제할 장바구니 항목 ID
     * @return 성공 메시지와 함께 HTTP 상태 코드 200(OK)를 반환
     */
    @DeleteMapping("/{userId}/{cartItemId}")
    public ResponseEntity<SuccessResponse<Void>> deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId) {

        // 사용자 아이디와 아이템 정보를 통해 삭제
        cartService.deleteCartItem(userId, cartItemId);

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "장바구니에서 상품을 성공적으로 삭제하였습니다.", null),
                HttpStatus.OK
        );
    }


}
