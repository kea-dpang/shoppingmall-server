package com.example.shoppingmallserver.entity.mileage;

import com.example.shoppingmallserver.base.BaseEntity;
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
    @Column(name = "cart_id")
    private Long cartId;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    // 사원 마일리지
    @Column(name = "employee_mileage")
    private int employeeMileage;

    // 개인 충전 마일리지
    @Column(name = "personalChargedMileage")
    private int personal_charged_mileage;

}
