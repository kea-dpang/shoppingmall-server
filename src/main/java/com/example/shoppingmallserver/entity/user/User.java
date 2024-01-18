package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;

import com.example.shoppingmallserver.entity.cart.CartItem;
import com.example.shoppingmallserver.entity.mileage.Mileage;
import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    // 사용자 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // 활성화 상태 (회원, 탈퇴)
    private UserStatus status;

    // 생성 날짜
    @Column(name = "created_at")
    private LocalDate createdAt;

    // 변경 날짜
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // User와 연관되어 있는 엔티티 연쇄 삭제하도록 연관관계 설정
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_detail_id")
    private UserDetail userDetail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<WishlistItem> wishlistItems;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mileage_id")
    private Mileage mileage;

}
