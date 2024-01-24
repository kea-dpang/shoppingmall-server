package com.example.shoppingmallserver.redis.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 인증 코드를 저장하는 클래스입니다.
 * 이 클래스는 Redis에 저장되며, 사용자 이메일을 키로 하고 인증 코드를 값으로 갖습니다.
 * 인증 코드는 5분 동안 유효합니다.
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "VerificationCode", timeToLive = 60 * 5) // 5분
public class VerificationCode {

    // 사용자 이메일. Redis에서 이 값을 키로 사용합니다.
    @Id
    @Indexed
    private String email;

    // 사용자 인증 번호.
    private String code;
}
