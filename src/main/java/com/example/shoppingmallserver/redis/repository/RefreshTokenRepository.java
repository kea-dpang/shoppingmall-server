package com.example.shoppingmallserver.redis.repository;

import com.example.shoppingmallserver.redis.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

/**
 * RefreshToken 엔티티에 대한 CRUD 작업을 수행하는 레포지토리 인터페이스입니다.
 */
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
