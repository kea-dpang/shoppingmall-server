package com.example.shoppingmallserver.controller;

import com.example.shoppingmallserver.base.BaseResponse;
import com.example.shoppingmallserver.base.SuccessResponse;
import com.example.shoppingmallserver.dto.request.cart_wishlist.CartItemRequestDto;
import com.example.shoppingmallserver.dto.response.cart_wishlist.ReadCartItemResponseDto;
import com.example.shoppingmallserver.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 장바구니 상품 정보를 관리하는 Controller 클래스입니다.
 * 장바구니 상품 조회, 추가, 삭제 기능을 제공합니다.
 */
@Tag(name = "Cart", description = "Cart 서비스 API")
@RestController
@RequestMapping("/api/carts/{userId}")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 사용자의 장바구니의 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 성공 응답 메시지와 함께 장바구니 내용을 담은 DTO를 반환
     */
    @PreAuthorize("#clientId == #userId")
    @GetMapping
    @Operation(summary = "장바구니 목록 조회", description = "사용자가 장바구니 목록을 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ReadCartItemResponseDto>>> getCartItemList(
            @RequestHeader("X-DPANG-CLIENT-ID") @Parameter(hidden = true) Long clientId,
            @RequestHeader("X-DPANG-CLIENT-ROLE") @Parameter(hidden = true) String role,
            @PathVariable @Parameter(description = "유저 ID(PK)", example = "3") Long userId) {

        // CartService에서 만든 메서드로 DTO를 받아온걸 포함하는 성공 응답 메시지를 생성하고, 이를 ResponseEntity로 감싸어 반환
        // 이를 통해 API 호출한 클라이언트에게 장바구니 정보가 성공적으로 조회되었음을 알림
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "사용자의 장바구니 정보를 성공적으로 조회하였습니다.", cartService.getCartItemList(userId)),
                HttpStatus.OK
        );
    }

    /**
     * 사용자의 장바구니에 아이템을 추가합니다.
     *
     * @param userId 사용자 ID
     * @param cartItemRequestDto 추가할 아이템의 ID와 수량이 들어있는 요청 정보
     * @return 성공 메시지와 함께 HTTP 상태 코드 201(CREATED)를 반환
     */
    @PreAuthorize("#clientId == #userId")
    @PostMapping
    @Operation(summary = "장바구니 상품 추가", description = "사용자가 장바구니 상품을 추가합니다.")
    public ResponseEntity<BaseResponse> addCartItem(
            @RequestHeader("X-DPANG-CLIENT-ID") @Parameter(hidden = true) Long clientId,
            @RequestHeader("X-DPANG-CLIENT-ROLE") @Parameter(hidden = true) String role,
            @PathVariable @Parameter(description = "사용자 ID(PK)", example = "1") Long userId,
            @RequestBody @Valid CartItemRequestDto cartItemRequestDto) {

        // 상품 정보와 사용자 아이디를 통해 장바구니에 아이템 추가
        cartService.addCartItem(userId, cartItemRequestDto.getItemId(), cartItemRequestDto.getQuantity());

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                    new BaseResponse(HttpStatus.CREATED.value(), "장바구니에 상품을 성공적으로 추가하였습니다."),
                HttpStatus.CREATED
        );
    }

    /**
     * 사용자의 장바구니에서 아이템에 해당하는 장바구니 항목을 삭제합니다.
     *
     * @param userId 사용자 ID
     * @param itemId 삭제할 장바구니 항목 ID
     * @return 성공 메시지와 함께 HTTP 상태 코드 204(NO_CONTENT)를 반환
     */
    @PreAuthorize("#clientId == #userId")
    @DeleteMapping("/{itemId}")
    @Operation(summary = "장바구니 상품 삭제", description = "사용자가 장바구니 상품을 삭제합니다.")
    public ResponseEntity<BaseResponse> deleteCartItem(
            @RequestHeader("X-DPANG-CLIENT-ID") @Parameter(hidden = true) Long clientId,
            @RequestHeader("X-DPANG-CLIENT-ROLE") @Parameter(hidden = true) String role,
            @PathVariable @Parameter(description = "사용자 ID(PK)", example = "3") Long userId,
            @PathVariable @Parameter(description = "상품 ID(PK)", example = "405") Long itemId) {

        // 사용자 아이디와 아이템 정보를 통해 삭제
        cartService.deleteCartItem(userId, itemId);

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.NO_CONTENT.value(), "장바구니에서 상품을 성공적으로 삭제하였습니다."),
                HttpStatus.NO_CONTENT
        );
    }

    /**
     * 사용자의 장바구니에서 아이템에 해당하는 장바구니 항목의 개수를 1개 감소시킵니다.
     *
     * @param userId 사용자 ID
     * @param itemId 감소할 장바구니 항목 ID
     * @return 성공 메시지와 함께 HTTP 상태 코드 204(NO_CONTENT)를 반환
     */
    @PreAuthorize("#clientId == #userId")
    @PostMapping("/{itemId}/minus")
    @Operation(summary = "장바구니 상품 1개 감소", description = "사용자가 장바구니 상품의 개수를 1개 감소 시킵니다.")
    public ResponseEntity<BaseResponse> minusCartItem(
            @RequestHeader("X-DPANG-CLIENT-ID") @Parameter(hidden = true) Long clientId,
            @RequestHeader("X-DPANG-CLIENT-ROLE") @Parameter(hidden = true) String role,
            @PathVariable @Parameter(description = "사용자 ID(PK)", example = "3") Long userId,
            @PathVariable @Parameter(description = "상품 ID(PK)", example = "405") Long itemId) {

        // 사용자 아이디와 아이템 정보를 통해 감소
        cartService.minusCartItem(userId, itemId);

        // API 호출한 곳에 전달
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.NO_CONTENT.value(), "장바구니에 상품의 개수를 성공적으로 감소시켰습니다."),
                HttpStatus.NO_CONTENT
        );
    }
}
