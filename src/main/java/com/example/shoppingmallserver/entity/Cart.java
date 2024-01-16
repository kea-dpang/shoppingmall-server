package com.example.shoppingmallserver.entity;

import com.example.shoppingmallserver.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity {

    // 카트 ID (PK) -> N번째 카트 이름이 아닌 카트에 든 데이터의 N번째 수
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    // 상품 ID (FK인데 변경 예정)
    private Long item_id;
}
