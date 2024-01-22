package com.example.shoppingmallserver.entity.wishlist_item;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 위시리스트 항목을 나타내는 엔티티 클래스입니다.
 * 위시리스트 항목 ID, 사용자, 상품 ID, 추가된 날짜 정보를 포함합니다.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "wishlist_item")
public class WishlistItem extends BaseEntity {

    // 위시리스트 아이템 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_item_id")
    private Long wishlistItemId;

    // 사용자 ID (FK) -> 사용자와 일대다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    // 상품 ID (FK인데 변경 예정)
    @Column(name = "item_id")
    private Long itemId;

    // 추가된 날짜
    @Column(name = "added_at")
    private LocalDate addedAt;

    @Builder
    public WishlistItem(User user, Long itemId, LocalDate addedAt) {
        this.user = user;
        this.itemId = itemId;
        this.addedAt = addedAt;
    }

}
