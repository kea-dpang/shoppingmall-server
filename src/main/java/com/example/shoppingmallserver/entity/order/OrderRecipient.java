package com.example.shoppingmallserver.entity.order;

import com.example.shoppingmallserver.base.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_recipient")
public class OrderRecipient extends BaseEntity {

    // 주문 ID (FK)
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "order_id")
    private Order order;

    // 받는 사람
    @Column(name = "receiver_name")
    private String receiverName;

    // 받는 사람 전화번호
    @Column(name = "receiver_phone_number")
    private String receiverPhoneNumber;

    // 받는 사람 우편번호
    @Column(name = "receiver_zip_code")
    private String receiverZipCode;

    // 받는 사람 주소
    @Column(name = "receiver_address")
    private String receiverAddress;

    // 받는 사람 상세 주소
    @Column(name = "receiver_detail_address")
    private String receiverDetailAddress;

}
