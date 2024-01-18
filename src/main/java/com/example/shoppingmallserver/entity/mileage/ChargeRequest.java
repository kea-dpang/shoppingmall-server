package com.example.shoppingmallserver.entity.mileage;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "charge_request")
public class ChargeRequest extends BaseEntity {

    // 충전 요청 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_request_id")
    private Long chargeRequestId;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "user_id")
    private User user;

}
