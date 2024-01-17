package com.example.shoppingmallserver.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "refund")
public class Refund {

    // 환불 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refund_id;

    // 주문 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "order_detail_id")
    private OrderDetail order_detail;

    // 환불 사유
    private RefundReason reason;

    // 비고
    private String note;

    // 환불 요청 날짜
    private LocalDate refund_request_date;

    // 환불 완료 날짜
    private LocalDate refund_complete_date;

    // 환불 예정액
    private int refund_amount;

    // 환불 상태
    private RefundStatus status;

}
