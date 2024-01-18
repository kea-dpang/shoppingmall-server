package com.example.shoppingmallserver.entity.order;

import com.example.shoppingmallserver.base.BaseEntity;
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
    @Column(name = "order_id")
    private Long orderId;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    // 주문 및 배송 상태
    private LocalDate status;

    // 배송시 요청사항
    @Column(name = "delivery_request")
    private String deliveryRequest;

    // 결제 금액
    @Column(name = "payment_amount")
    private int paymentAmount;

    // 생성 날짜
    @Column(name = "create_at")
    private LocalDate createAt;

    // 변경 날짜
    private LocalDate updated_at;

}
