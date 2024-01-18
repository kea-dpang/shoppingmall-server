package com.example.shoppingmallserver.entity.order;

import com.example.shoppingmallserver.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "refund")
public class Refund extends BaseEntity {

    // 환불 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private Long refundId;

    // 주문 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "order_detail_id")
    private OrderDetail orderDetail;

    // 환불 사유
    private RefundReason reason;

    // 비고
    private String note;

    // 환불 요청 날짜
    @Column(name = "refund_request_date")
    private LocalDate refundRequestDate;

    // 환불 완료 날짜
    @Column(name = "refund_complete_date")
    private LocalDate refundCompleteDate;

    // 환불 예정액
    @Column(name = "refund_amount")
    private int refundAmount;

    // 환불 상태
    private RefundStatus status;

}
