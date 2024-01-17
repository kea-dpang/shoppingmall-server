package com.example.shoppingmallserver.entity;

import com.example.shoppingmallserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mileage")
public class Mileage extends BaseEntity {

    // 카트 ID (PK) -> N번째 카트 이름이 아닌 카트에 든 데이터의 N번째 수
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    // 사원 마일리지
    private int employee_mileage;

    // 개인 충전 마일리지
    private int personal_charged_mileage;

}
