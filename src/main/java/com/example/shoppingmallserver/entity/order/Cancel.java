package com.example.shoppingmallserver.entity.order;

import com.example.shoppingmallserver.base.BaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cancel")
public class Cancel extends BaseEntity {

    // 주문 ID (FK)
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "order_id")
    private Order order;

    // 취소 요청 날짜
    private LocalDate cancel_request_date;

    // 취소 완료 날짜
    private LocalDate cancel_complete_date;

}
