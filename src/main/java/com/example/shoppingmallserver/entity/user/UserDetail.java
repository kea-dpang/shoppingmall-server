package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;

import jakarta.persistence.*;

import lombok.Builder;
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
    @Column(name = "employee_number")
    private Long employeeNumber;

    // 입사 날짜
    @Column(name = "join_date")
    private LocalDate joinDate;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 전화번호
    @Column(name = "phone_number")
    private String phoneNumber;

    // 우편번호
    @Column(name = "zip_code")
    private String zipCode;

    // 주소
    private String address;

    // 상세주소
    @Column(name = "detail_address")
    private String detailAddress;

    @Builder
    public UserDetail(User user, Long employeeNumber, String email, String name, LocalDate joinDate,
                      String phoneNumber, String zipCode, String address, String detailAddress) {
        this.user = user;
        this.employeeNumber = employeeNumber;
        this.email = email;
        this.name = name;
        this.joinDate = joinDate;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
    }

}
