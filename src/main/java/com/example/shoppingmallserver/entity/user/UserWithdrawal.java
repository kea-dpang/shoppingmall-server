package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_withdrawal")
@Builder
@AllArgsConstructor
public class UserWithdrawal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_withdrawal_id")
    private Long id;

    // 탈퇴 사유 (1. 고객서비스 불만 2. ~~)
    private List<WithdrawalReason> reason;

    // 남길 말씀
    private String message;

    // 탈퇴 날짜
    @Column(name = "withdrawal_date")
    private LocalDate withdrawalDate;

}
