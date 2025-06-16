package src.global.common.security.jwt;

import javax.crypto.SecretKey;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import src.global.config.JwtTokenConfig;
import src.global.config.JwtTokenConfig.TokenProperties;

/**
 * PackageName : src.global.common.security.jwt;
 * FileName    : JwtAuthenticationFilter
 * Author      : jh
 * Date        : 25. 5. 26.
 * Description : 토큰 생성 및 검증 등에 필요한 secret key와 토큰 만료 시간 설정
 */
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
		this.secretKey = Keys.hmacShaKeyFor(accessProps.getSecret().getBytes());
		this.expirationMillis = accessProps.getExpirationSeconds() * 1000;
	}

}
