package com.example.shoppingmallserver.redis.entity;

/**
 * 리프레쉬 토큰을 저장하는 클래스입니다.
 * 이 클래스는 Redis에 저장되며, 사용자 ID를 키로 하고 리프레쉬 토큰을 값으로 갖습니다.
 * 리프레쉬 토큰은 5일 동안 유효합니다.
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * Refresh token을 저장하는 클래스입니다.
 * 이 클래스는 Redis에 저장되며, 사용자 ID를 키로 하고 refresh token을 값으로 갖습니다.
 * Refresh token은 5일 동안 유효합니다.
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "RefreshToken", timeToLive = 60 * 60 * 24 * 5) // 5일
public class RefreshToken {

        // 사용자 ID. Redis에서 이 값을 키로 사용합니다.
        @Id
        @Indexed
        private Long id;

        // 사용자의 refresh token.
        private String refreshToken;
}