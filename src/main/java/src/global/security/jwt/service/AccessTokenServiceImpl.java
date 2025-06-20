package src.global.security.jwt.service;

import java.util.Date;
import java.nio.charset.StandardCharsets;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import src.global.security.config.JwtTokenConfig;
import src.global.security.config.JwtTokenConfig.TokenProperties;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl {

	private SecretKey secretKey;
	private long expirationMillis;

	private final JwtTokenConfig jwtTokenConfig;

	@PostConstruct
	public void init() {
		TokenProperties accessProps = jwtTokenConfig.getAccessToken();
		this.secretKey = Keys.hmacShaKeyFor(accessProps.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.expirationMillis = accessProps.getExpirationSeconds() * 1000L;
	}

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

	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

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
