package src.global.security.jwt.service;

import java.security.Key;
import java.util.Date;
import java.nio.charset.StandardCharsets;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;

import jakarta.annotation.PostConstruct;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import src.global.security.config.JwtTokenConfig;
import src.global.security.config.JwtTokenConfig.TokenProperties;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

	private SecretKey secretKey;

	private long expirationMillis;

	private final JwtTokenConfig jwtTokenConfig;

	private final RedisTemplate<String, Object> redisTemplate;

	private static final String ACCESS_PREFIX = "access_token:";


	// ✅ AccessToken용 키 생성
	@Override
	public String createAccessToken(String userId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMillis);

		return Jwts.builder()
			.setSubject(userId)
			.setIssuer(jwtTokenConfig.getAccessToken().getIssuer())
			.setAudience(jwtTokenConfig.getAccessToken().getAudience())
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	private Key getAccessTokenKey() {
		return Keys.hmacShaKeyFor(
			jwtTokenConfig.getAccessToken().getSecretKey().getBytes(StandardCharsets.UTF_8)
		);
	}

	@Override
	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

	@Override
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)  // 또는 getAccessTokenKey()
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			System.out.println("JWT 만료: " + e.getMessage());
		} catch (JwtException e) {
			System.out.println("JWT 유효하지 않음: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("JWT 파싱 오류: " + e.getMessage());
		}
		return false;
	}

	public String extractUserId(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
