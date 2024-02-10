package com.example.shoppingmallserver.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정을 위한 클래스입니다.
 */
@Configuration
public class SecurityConfig {

    DpangServiceNameHeaderFilter dpangServiceNameHeaderFilter = new DpangServiceNameHeaderFilter();

    /**
     * Spring Security의 필터 체인을 구성합니다.
     * @param http HttpSecurity 인스턴스
     * @return 구성된 SecurityFilterChain 인스턴스
     * @throws Exception 구성 중 발생하는 모든 예외를 던집니다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable) // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf(AbstractHttpConfigurer::disable) // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // jwt token으로 인증 -> 세션은 필요없으므로 생성안함.
                .cors(AbstractHttpConfigurer::disable)// CORS(Cross-Origin Resource Sharing) 설정 비활성화.
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // X-Frame-Options 비활성화 (IFrame 사용 가능하도록).
                .formLogin(AbstractHttpConfigurer::disable) // formLogin 대신 Jwt를 사용하기 때문에 disable로 설정
                .logout(AbstractHttpConfigurer::disable) // 로그아웃 기능 비활성화.
                .authorizeHttpRequests(
                        request -> request
                                .anyRequest().authenticated() // 모든 요청이 인증을 필요로 하도록 설정
                )
                .addFilterBefore(dpangServiceNameHeaderFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
