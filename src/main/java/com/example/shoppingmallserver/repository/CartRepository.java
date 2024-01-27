package com.example.shoppingmallserver.repository;

import com.example.shoppingmallserver.entity.cart.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 장바구니 항목에 대한 데이터 접근을 담당하는 레포지토리 인터페이스입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 지원합니다.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

  /**
   * 사용자 ID로 장바구니를 조회합니다.
   *
   * @param userId 조회할 사용자의 ID
   * @return 조회된 장바구니
   */
  Cart findCartByUserId(Long userId);

  // JPQL을 사용하여 특정 userId와 itemId에 해당하는 Cart를 삭제하는 쿼리
  @Modifying
  @Query("DELETE FROM Cart c WHERE c.user.id = :userId AND :itemId MEMBER OF c.itemIds")
  void deleteByUserIdAndItemId(Long userId, Long itemId);
  
}
