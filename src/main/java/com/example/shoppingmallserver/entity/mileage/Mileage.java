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

    // 사용자 ID (FK)
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

    // 사원 마일리지
    @Column(name = "employee_mileage")
    private int employeeMileage;

    // 개인 충전 마일리지
    @Column(name = "personal_charged_mileage")
    private int personalChargedMileage;

}
