package com.example.shoppingmallserver.entity.cart;

import com.example.shoppingmallserver.base.BaseEntity;

import com.example.shoppingmallserver.entity.user.User;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 항목을 나타내는 엔티티 클래스입니다.
 * 장바구니 항목 ID, 사용자, 상품 ID의 List, 수량, 포함합니다.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {

    // 장바구니 ID (PK) = 유저 아이디와 동일하다고 보면 됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    // 상품 ID
    @ElementCollection
    @Column(name = "item_id")
    private List<Long> itemIds;

    // 수량
    private int quantity;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 상품 ID, 수량을 이용하여 새로운 Cart 엔티티를 생성합니다.
     *
     * @param itemId 장바구니에 추가된 상품의 ID
     * @param quantity 장바구니 항목의 수량
     */
    @Builder
    public Cart(Long itemId, int quantity) {
        itemIds = new ArrayList<>();
        itemIds.add(itemId);
        this.quantity += quantity;
    }
}