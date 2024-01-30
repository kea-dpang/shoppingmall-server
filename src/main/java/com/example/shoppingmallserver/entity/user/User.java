package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.base.Role;
import com.example.shoppingmallserver.entity.cart.Cart;
import com.example.shoppingmallserver.entity.wishlist.Wishlist;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자를 나타내는 엔티티 클래스입니다.
 * 사용자 ID, 상태(활성화 상태), 생성 날짜, 변경 날짜 정보를 포함합니다.
 * 사용자 상세, 장바구니 항목, 위시리스트 항목, 마일리지와의 관계를 정의합니다.
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User extends BaseEntity {

    // 사용자 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;  // 이메일

    private String password;  // 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;  // 권한*/

    // 활성화 상태 (회원, 탈퇴)
    private UserStatus status;

    // 유저와 유저상세는 일대일 관계
    @OneToOne(mappedBy = "user")
    private UserDetail userDetail;

    // 유저와 카트는 일대일 관계
    @OneToOne(mappedBy = "user")
    private Cart cart;

    // 유저와 위시리스트는 일대일 관계
    @OneToOne(mappedBy = "user")
    private Wishlist wishlist;

    /**
     * 비밀번호 변경 메서드
     *
     * @param newPassword 새 비밀번호
     */
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
