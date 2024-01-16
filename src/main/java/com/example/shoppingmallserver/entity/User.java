package com.example.shoppingmallserver.entity;

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
    private Long id;

    // 사원번호
    @Id
    @Column(nullable = false)
    private Long employee_number;

    // 입사 날짜
    private LocalDate join_date;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 비밀번호
    private String password;

    // 전화번호
    private String phone_number;

    // 우편번호
    private String zip_code;

    // 주소
    private String address;

    // 상세주소
    private String detail_address;

    // 역할
    private String role;
}
