package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;

import com.example.shoppingmallserver.entity.cart.Cart;
import com.example.shoppingmallserver.entity.mileage.Mileage;
import com.example.shoppingmallserver.entity.wishlist_item.WishlistItem;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 사용자를 나타내는 엔티티 클래스입니다.
 * 사용자 ID, 상태(활성화 상태), 생성 날짜, 변경 날짜 정보를 포함합니다.
 * 사용자 상세, 장바구니 항목, 위시리스트 항목, 마일리지와의 관계를 정의합니다.
 */
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

    // 유저와 카트는 일대일 관계 (유저가 삭제되면 연쇄적으로 삭제)
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Cart cart;

    // 유저와 위시리스트는 일대다 관계 (유저가 삭제되면 연쇄적으로 삭제)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<WishlistItem> wishlistItems;

    // 유저와 마일리지는 일대일 관계 (유저가 삭제되면 연쇄적으로 삭제)
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mileage_id")
    private Mileage mileage;

    /**
     * 상태, 생성 날짜, 변경 날짜를 이용하여 새로운 User 엔티티를 생성합니다.
     *
     * @param status 사용자의 상태(활성화 상태)
     * @param createdAt 사용자가 생성된 날짜
     * @param updatedAt 사용자 정보가 변경된 날짜
     */
    @Builder
    public User(UserStatus status, LocalDate createdAt, LocalDate updatedAt) {
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
