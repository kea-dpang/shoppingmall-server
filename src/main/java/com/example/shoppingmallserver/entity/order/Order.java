package com.example.shoppingmallserver.entity.order;

import com.example.shoppingmallserver.entity.BaseEntity;
import com.example.shoppingmallserver.entity.user.User;

import jakarta.persistence.*;

import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "order")
public class Order extends BaseEntity {

    // 주문 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    // 주문 및 배송 상태
    private LocalDate status;

    // 배송시 요청사항
    private String delivery_request;

    // 결제 금액
    private int payment_amount;

    // 생성 날짜
    private LocalDate create_at;

    // 변경 날짜
    private LocalDate updated_at;

}
