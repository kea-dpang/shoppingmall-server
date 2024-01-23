package com.example.shoppingmallserver.utils;

import com.example.shoppingmallserver.dto.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

/**
 * JWT 토큰을 생성하고 관리하는 컴포넌트입니다.
 */
@Component
public class JwtTokenProvider {

    private SecretKey secretKey;

    // 1시간을 밀리초로 표현한 값입니다.
    private static final long ONE_HOUR = 1000L * 60 * 60;
    // 1일을 밀리초로 표현한 값입니다.
    private static final long ONE_DAY = ONE_HOUR * 24;
    // 액세스 토큰의 만료 시간입니다. 현재는 3시간으로 설정되어 있습니다.
    private static final long ACCESS_TOKEN_EXPIRE_TIME = ONE_HOUR * 3;
    // 리프레쉬 토큰의 만료 시간입니다. 현재는 5일로 설정되어 있습니다.
    private static final long REFRESH_TOKEN_EXPIRE_TIME = ONE_DAY * 5;

    /**
     * JwtTokenProvider의 생성자입니다.
     *
     * @param secretKey jwt 토큰 생성을 위한 비밀 키입니다. 이 키는 애플리케이션 설정에서 주입받습니다.
     */
    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    /**
     * 사용자 인증 정보를 바탕으로 액세스 토큰과 리프레시 토큰을 생성하고,
     * 이를 포함하는 Token 객체를 반환하는 메소드입니다.
     *
     * @param authentication 사용자 인증 정보
     * @param userIdx 사용자 ID
     * @return 생성된 액세스 토큰과 리프레시 토큰을 포함하는 Token 객체
     * @throws Exception 토큰 생성 과정에서 발생할 수 있는 예외를 던집니다.
     */
    public Token createTokens(Authentication authentication, long userIdx) throws Exception {
        return new Token(
                createAccessToken(authentication, userIdx),
                createRefreshToken(authentication)
        );
    }

    /**
     * 액세스 토큰을 생성하는 함수
     * @param authentication 인증 정보
     * @param userIdx 사용자 ID
     * @return 생성된 액세스 토큰
     * @throws Exception 예외
     */
    public String createAccessToken(Authentication authentication, long userIdx) throws Exception {

        // 사용자의 권한을 가져온 후 콤마로 분리된 문자열로 변환
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // JWT 액세스 토큰을 생성하고 반환
        return Jwts.builder()
                // Header: 토큰의 타입(JWT)과 서명에 사용된 알고리즘(HS512) 정보를 담는다.
                .setHeaderParam("type", "JWT") // 토큰의 타입 지정. 여기서는 JWT를 사용.
                // Payload: 토큰에 담을 클레임(데이터)을 지정. 클레임에는 사용자의 이름, 역할, ID 등이 포함될 수 있다.
                .issuer("DPANG-AUTH-SERVER") // iss 클레임: 토큰 발급자를 지정
                .subject(authentication.getName()) // sub 클레임: 토큰 제목을 지정
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME)) // exp 클레임: 토큰 만료 시간을 지정
                .issuedAt(new Date()) // iat 클레임: 토큰 발급 시간을 지정
                .claim("role", roles) // 사용자 정의 클레임: 사용자의 역할
                .claim("client-id", userIdx) // 사용자 정의 클레임: 사용자의 식별자
                // Signature: header와 payload를 암호화한 결과. 이 부분이 토큰의 무결성을 보장하는 부분.
                .signWith(secretKey) // signWith 메소드를 사용해 서명 알고리즘과 키를 지정
                .compact();
    }

    /**
     * 리프레시 토큰을 생성하는 메소드
     *
     * @param authentication 사용자 인증 정보를 담고 있는 Authentication 객체
     * @return 생성된 JWT 리프레시 토큰
     * @throws Exception 토큰 생성 중 발생한 예외를 처리하기 위해 사용됨
     */
    public String createRefreshToken(Authentication authentication) throws Exception {

        // JWT 리프레시 토큰을 생성하고 반환
        return Jwts.builder()
                .subject(authentication.getName()) // sub 클레임: 토큰 제목을 지정
                .issuer("DPANG-AUTH-SERVER") // iss 클레임: 토큰 발급자를 지정
                .issuedAt(new Date()) // iat 클레임: 토큰 발급 시간을 지정
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME)) // exp 클레임: 토큰 만료 시간을 지정
                .signWith(secretKey) // signWith 메소드를 사용해 서명 알고리즘과 키를 지정
                .compact(); // 마지막으로 compact 메소드를 호출해 모든 부분을 합쳐서 하나의 JWT 토큰 문자열을 생성한다
    }

    /**
     * Access token에서 식별자를 추출하는 메소드
     *
     * @param token Access token
     * @return 토큰에서 추출된 식별자
     */
    public Long getClientIdFromAccessToken(String token) {

        // JJWT에서 제공하는 parserBuilder를 통해 JwtParser를 생성
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        // 토큰을 파싱하여 Claims 객체 획득
        Jws<Claims> jwsClaims = jwtParser.parseSignedClaims(token);
        Claims claims = jwsClaims.getPayload();

        // Claims에서 client-id 추출
        return claims.get("client-id", Long.class);
    }
}