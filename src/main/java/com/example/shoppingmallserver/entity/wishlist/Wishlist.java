package com.example.shoppingmallserver.entity.wishlist;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 위시리스트 항목을 나타내는 엔티티 클래스입니다.
 * 위시리스트 항목 ID, 사용자, 상품 ID, 추가된 날짜 정보를 포함합니다.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "wishlist")
public class Wishlist extends BaseEntity {

    // 위시리스트 ID (PK) = 유저 아이디와 동일하다고 보면 됨
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    // 상품 ID
    @ElementCollection
    @Column(name = "item_id")
    private List<Long> itemIds;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 상품 ID를 이용하여 새로운 Wishlist 엔티티를 생성합니다.
     *
     * @param itemId 장바구니에 추가된 상품의 ID
     */
    @Builder
    public Wishlist(Long itemId) {
        itemIds = new ArrayList<>();
        itemIds.add(itemId);
    }

}
