package com.example.shoppingmallserver.entity.cart;

import com.example.shoppingmallserver.base.BaseEntity;

import com.example.shoppingmallserver.entity.user.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 장바구니 항목을 나타내는 엔티티 클래스입니다.
 * 장바구니 항목 ID, 사용자, 상품 ID의 List, 수량, 포함합니다.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
@Builder
@AllArgsConstructor
public class Cart extends BaseEntity {

    // 장바구니 ID (PK) = 유저 아이디와 동일하다고 보면 됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    // 상품 ID -> 상품에 맞는 수량을 넣도록 변경
    @ElementCollection
    private Map<Long, Integer> items; // 장바구니에 담긴 상품들. key는 상품 ID, value는 수량

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 장바구니 엔티티에 상품 추가하는 메서드
     *
     * @param itemId 장바구니에 추가된 상품의 ID
     */
    public void addItem(Long itemId, int quantity) {
        // 이미 장바구니에 동일한 상품이 있는지 확인
        Integer currentQuantity = items.get(itemId);
        if (currentQuantity == null) {
            // 장바구니에 동일한 상품이 없는 경우, 새로운 아이템을 추가
            items.put(itemId, quantity);
        } else {
            // 장바구니에 동일한 상품이 있는 경우, 수량을 증가
            items.put(itemId, currentQuantity + quantity);
        }
    }

    /**
     * 장바구니 엔티티에 상품 삭제하는 메서드
     *
     * @param itemId 장바구니에 추가된 상품의 ID
     */
    public void removeItem(Long itemId) {
        items.remove(itemId);
    }
}