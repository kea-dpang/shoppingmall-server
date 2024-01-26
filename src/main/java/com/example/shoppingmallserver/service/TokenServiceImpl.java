package com.example.shoppingmallserver.service;

import com.example.shoppingmallserver.dto.Token;
import com.example.shoppingmallserver.entity.user.User;
import com.example.shoppingmallserver.exception.InvalidRefreshTokenException;
import com.example.shoppingmallserver.exception.TokenNotFoundException;
import com.example.shoppingmallserver.exception.UserNotFoundException;
import com.example.shoppingmallserver.redis.entity.RefreshToken;
import com.example.shoppingmallserver.redis.repository.RefreshTokenRepository;
import com.example.shoppingmallserver.repository.UserRepository;
import com.example.shoppingmallserver.utils.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 사용자를 위한 토큰을 생성하는 메소드
     *
     * @param userId 사용자의 ID
     * @return 생성된 토큰
     */
    @Override
    public Token createToken(Long userId) throws Exception {
        // 사용자 정보를 조회한다.
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));

        // 사용자의 권한을 설정한다.
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);

        // JWT 토큰을 생성한다.
        Token jwtToken = jwtTokenProvider.createTokens(authentication, userId);

        // 이미 Redis에 해당 사용자의 리프레시 토큰이 존재한다면 삭제합니다.
        if (tokenRepository.existsById(userId)) {
            tokenRepository.deleteById(userId);
        }

        // Redis에 새로운 리프레시 토큰을 저장합니다.
        RefreshToken refreshToken = new RefreshToken(userId, jwtToken.getRefreshToken());
        tokenRepository.save(refreshToken);

        return jwtToken;
    }

    /**
     * 기존의 액세스 토큰을 기반으로 새로운 토큰을 생성하는 메소드
     *
     * @param accessToken 기존의 액세스 토큰
     * @return 생성된 토큰
     * @throws UserNotFoundException 해당 ID를 가진 사용자를 찾을 수 없을 경우 발생
     * @throws InvalidRefreshTokenException 리프레시 토큰이 유효하지 않을 경우 발생
     */
    @Override
    public Token refreshToken(String accessToken) throws Exception {
        // 액세스 토큰에서 사용자 ID를 추출한다.
        Long userId = jwtTokenProvider.getClientIdFromAccessToken(accessToken);

        // MySQL에서 사용자 정보를 가져온다.
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));

        // Redis에서 리프레시 토큰을 가져온다.
        Optional<RefreshToken> refreshTokenInRedis = tokenRepository.findById(userId);

        // Redis의 리프레시 토큰과 클라이언트의 리프레시 토큰이 일치하면 기존 토큰을 파기하고 새로운 토큰을 발급한다.
        if (refreshTokenInRedis.isPresent() && refreshTokenInRedis.get().getRefreshToken().equals(accessToken)) {
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);

            // 리프레시 토큰 파기
            tokenRepository.deleteById(userId);

            return jwtTokenProvider.createTokens(authentication, userId);

        } else {
            throw new InvalidRefreshTokenException(accessToken);
        }
    }

    /**
     * 토큰을 제거하는 메소드
     *
     * @param userId 사용자의 ID
     * @throws TokenNotFoundException 토큰을 찾을 수 없을 경우 발생
     */
    @Override
    public void removeToken(Long userId) {
        try {
            tokenRepository.deleteById(userId);

        } catch (EmptyResultDataAccessException e) { // 토큰이 없는 경우에 대한 처리
            throw new TokenNotFoundException(userId);
        }
    }
}

