package com.example.shoppingmallserver.entity.cart;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.entity.user.User;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem extends BaseEntity {

    // 장바구니 아이템 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    // 사용자 ID (FK) -> 사용자와 일대다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    // 상품 ID (FK인데 변경 예정)
    @Column(name = "item_id")
    private Long itemId;

    // 수량
    private int quantity;

    // 추가된 날짜
    @Column(name = "added_at")
    private LocalDate addedAt;

}