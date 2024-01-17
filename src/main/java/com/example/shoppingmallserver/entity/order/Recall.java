package com.example.shoppingmallserver.entity.order;

import com.example.shoppingmallserver.base.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "recall")
public class Recall extends BaseEntity {

    // 환불 ID (FK)
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "refund_id")
    private Refund refund;

    // 회수자명
    private String retrieval_name;

    // 회수자 연락처
    private String retrieval_phone_number;

    // 회수자 주소
    private String retrieval_address;

    // 회수 메시지
    private String retrieval_message;
}
