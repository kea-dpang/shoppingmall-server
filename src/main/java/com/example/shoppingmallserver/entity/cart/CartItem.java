package com.example.shoppingmallserver.entity.cart;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.entity.user.User;

import com.example.shoppingmallserver.entity.user.UserStatus;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 장바구니 항목을 나타내는 엔티티 클래스입니다.
 * 장바구니 항목 ID, 사용자, 상품 ID, 수량, 추가된 날짜 정보를 포함합니다.
 */
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

    /**
     * 사용자, 상품 ID, 수량, 추가된 날짜를 이용하여 새로운 CartItem 엔티티를 생성합니다.
     *
     * @param user 장바구니 항목을 소유한 사용자
     * @param itemId 장바구니에 추가된 상품의 ID
     * @param quantity 장바구니 항목의 수량
     * @param addedAt 장바구니 항목이 추가된 날짜
     */
    @Builder
    public CartItem(User user, Long itemId, int quantity, LocalDate addedAt) {
        this.user = user;
        this.itemId = itemId;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

}