package com.example.shoppingmallserver.entity.cart;

import com.example.shoppingmallserver.entity.user.User;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {

    // 장바구니 아이템 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_item_id;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    // 상품 ID (FK인데 변경 예정)
    private Long item_id;

    // 수량
    private int quantity;

    // 추가된 날짜
    private LocalDate added_at;

}