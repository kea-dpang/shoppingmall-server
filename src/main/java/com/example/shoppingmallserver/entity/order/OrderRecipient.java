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
    private String receiver_name;

    // 받는 사람 전화번호
    private String receiver_phone_number;

    // 받는 사람 우편번호
    private String receiver_zip_code;

    // 받는 사람 주소
    private String receiver_address;

    // 받는 사람 상세 주소
    private String receiver_detail_address;

}
