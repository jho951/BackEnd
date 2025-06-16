package src.global.common.security.auth.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.servlet.http.HttpServletRequest;

import src.global.config.JwtTokenConfig;
import src.global.common.security.user.CustomUserDetailsService;

/**
 * JWT 발급 및 해석, 인증 객체 생성까지 전반적인 JWT 처리를 담당하는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtTokenConfig jwtTokenConfig;

	// ✅ AccessToken용 키 생성
	private Key getAccessTokenKey() {
		return Keys.hmacShaKeyFor(
			jwtTokenConfig.getAccessToken().getSecretKey().getBytes(StandardCharsets.UTF_8)
		);
	}

	// ✅ JWT 생성
	public String createToken(String userId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtTokenConfig.getAccessToken().getExpirationSeconds() * 1000L);

		return Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(getAccessTokenKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	// ✅ 요청 헤더에서 토큰 추출
	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

	// ✅ 토큰 유효성 검사
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getAccessTokenKey())
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

	// ✅ 인증 객체 생성
	public Authentication getAuthentication(String token) {
		String userId = getUserIdFromToken(token);
		var userDetails = customUserDetailsService.loadUserByUsername(userId);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	// ✅ 토큰에서 사용자 ID 추출
	private String getUserIdFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getAccessTokenKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
