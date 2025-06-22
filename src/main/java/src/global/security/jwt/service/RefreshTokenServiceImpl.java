package src.global.security.jwt.service;

import java.util.Date;
import java.time.Duration;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;

import jakarta.annotation.PostConstruct;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import src.global.security.config.JwtTokenConfig;
import src.global.security.config.JwtTokenConfig.TokenProperties;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
	// JWT를 생성 시 서명 키 ( HMAC 방식 )
	private SecretKey secretKey;
	// 만료 시간(ms 단위)을 저장
	private long expirationMillis;
	//  JWT 구성 정보
	private final JwtTokenConfig jwtTokenConfig;

	private final RedisTemplate<String, Object> redisTemplate;

	private static final String REFRESH_PREFIX = "refresh_token:";



	// ✅ Refresh Token 생성 및 Redis 저장
	@Transactional
	@Override
	public String createAndSaveRefreshToken(String userId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMillis);

		String token = Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(secretKey)
			.compact();

		// Redis에 저장
		redisTemplate.opsForValue().set(
			REFRESH_PREFIX + userId,
			token,
			Duration.ofMillis(expirationMillis)
		);

		return token;
	}

	@Transactional
	@Override
	// ✅ 토큰 유효성 검증
	public boolean validateRefreshToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody();

			String userId = claims.getSubject();
			String savedToken = (String) redisTemplate.opsForValue().get(REFRESH_PREFIX + userId);
			return token.equals(savedToken);
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	@Override
	// ✅ 토큰 제거
	public void deleteRefreshToken(String userId) {
		redisTemplate.delete(REFRESH_PREFIX + userId);
	}
}