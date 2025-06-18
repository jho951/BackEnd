package src.global.security.jwt;

import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import src.global.security.config.JwtTokenConfig;
import src.global.security.config.JwtTokenConfig.TokenProperties;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

	// HMAC 서명을 위한 비밀 키로 JWT 생성 및 검증 시 사용됩니다.
	private SecretKey secretKey;

	// 토큰 만료 시간
	private long expirationMillis;

	// 설정한 JWT 관련 값을 외부 설정파일 주입
	private final JwtTokenConfig jwtTokenConfig;

	@PostConstruct
	public void init() {
		TokenProperties accessProps = jwtTokenConfig.getAccessToken();
		this.secretKey = Keys.hmacShaKeyFor(accessProps.getSecretKey().getBytes());
		this.expirationMillis = accessProps.getExpirationSeconds() * 1000;
	}
}
