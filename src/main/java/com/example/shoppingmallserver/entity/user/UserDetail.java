package com.example.shoppingmallserver.entity.user;

import com.example.shoppingmallserver.base.BaseEntity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사용자의 상세 정보를 나타내는 엔티티 클래스입니다.
 * 사용자, 사원 번호, 입사 날짜, 이름, 이메일, 전화번호, 우편번호, 주소, 상세주소 정보를 포함합니다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_detail")
public class UserDetail extends BaseEntity {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long id;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 사원번호
    @Column(name = "employee_number")
    private Long employeeNumber;

    // 입사 날짜
    @Column(name = "join_date")
    private LocalDate joinDate;

    // 이름
    private String name;

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

    public void changeAddress(UserDetail userDetail) {
        zipCode = userDetail.getZipCode();
        address = userDetail.getAddress();
        detailAddress = userDetail.getDetailAddress();
    }
}
