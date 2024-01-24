package com.example.shoppingmallserver.entity.auth;

import com.example.shoppingmallserver.base.BaseEntity;
import com.example.shoppingmallserver.base.Role;
import jakarta.persistence.*;
import lombok.*;


/**
 * 사용자 정보를 담는 엔티티 클래스입니다.
 * 이 클래스는 데이터베이스의 User 테이블과 매핑됩니다.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long userIdx; // 사용자 인덱스

    private String email;  // 이메일

    private String password;  // 비밀번호

    @Enumerated(EnumType.STRING)
    private Role role;  // 권한*/

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
