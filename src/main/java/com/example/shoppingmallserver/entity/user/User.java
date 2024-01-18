package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

}
