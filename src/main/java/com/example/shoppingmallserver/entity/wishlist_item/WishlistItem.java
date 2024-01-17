package com.example.shoppingmallserver.entity.wishlist_item;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "wishlist_item")
public class WishlistItem extends BaseEntity {

    // 위시리스트 아이템 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlist_item_id;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    // 상품 ID (FK인데 변경 예정)
    private Long item_id;

    // 추가된 날짜
    private LocalDate added_at;

}
