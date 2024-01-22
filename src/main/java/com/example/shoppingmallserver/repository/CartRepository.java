package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.cart.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 장바구니 항목에 대한 데이터 접근을 담당하는 레포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 지원합니다.
 */
public interface CartRepository extends JpaRepository<CartItem, Long> {

  /**
   * 사용자 ID로 장바구니 상품 리스트를 조회합니다.
   *
   * @param userId 조회할 사용자의 ID
   * @return 조회된 장바구니 항목 리스트
   */
  List<CartItem> findCartItemListByUserId(Long userId);

  /**
   * 사용자 ID로 장바구니 상품을 조회합니다.
   *
   * @param userId 조회할 사용자의 ID
   * @return 조회된 장바구니 항목
   */
  CartItem findCartItemByUserId(Long userId);
  
}
