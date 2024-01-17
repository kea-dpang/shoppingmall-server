package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.entity.BaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_withdrawal")
public class UserWithdrawal extends BaseEntity {

    // 사용자 ID (FK)
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    // 탈퇴 사유 (1. 고객서비스 불만 2. ~~)
    private WithdrawalReason reason;

    // 남길 말씀
    private String message;

    // 탈퇴 날짜
    private LocalDate withdrawal_date;

}
