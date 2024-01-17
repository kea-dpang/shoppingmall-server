package com.example.shoppingmallserver.entity.order;

import com.example.shoppingmallserver.base.BaseEntity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

    // 주문 상세 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_detail_id;

    // 주문 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "order_id")
    private Order order;

    // 상품 ID (FK인데 변경 예정)
    private Long item_id;

    // 수량
    private int quantity;

}