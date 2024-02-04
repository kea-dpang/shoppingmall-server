package com.example.shoppingmallserver.exception;

public class ItemNotInCartException extends RuntimeException {

    /**
     * 상품 ID를 통해 장바구니 상품을 찾을 수 없을 때 이 예외를 생성합니다.
     *
     * @param itemId 찾을 수 없는 상품의 ID
     */
    public ItemNotInCartException(Long itemId) {
        super("해당 상품이 장바구니에 없습니다. ID: " + itemId);
    }
}
