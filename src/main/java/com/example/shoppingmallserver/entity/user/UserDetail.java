package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_detail")
public class UserDetail extends BaseEntity {

    // 사용자 ID (FK)
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    // 사원번호
    @Column(nullable = false)
    private Long employee_number;

    // 입사 날짜
    private LocalDate join_date;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 전화번호
    private String phone_number;

    // 우편번호
    private String zip_code;

    // 주소
    private String address;

    // 상세주소
    private String detail_address;

}
