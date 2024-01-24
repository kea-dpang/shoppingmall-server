package com.example.shoppingmallserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA 설정을 위한 클래스입니다.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.shoppingmallserver.repository")
public class JpaConfig {
    // 필요한 경우 JPA 설정을 여기에 추가합니다.
}
