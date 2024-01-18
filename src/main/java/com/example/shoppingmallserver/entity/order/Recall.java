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
    @Column(name = "retrieval_name")
    private String retrievalName;

    // 회수자 연락처
    @Column(name = "retrieval_phone_number")
    private String retrievalPhoneNumber;

    // 회수자 주소
    @Column(name = "retrieval_address")
    private String retrievalAddress;

    // 회수 메시지
    @Column(name = "retrieval_message")
    private String retrievalMessage;

}
