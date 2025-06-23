package src.global.security.jwt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

import jakarta.annotation.PostConstruct;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import src.global.security.jwt.constant.TokenType;

@Component
@Getter
@RequiredArgsConstructor
public class JwtKeyConfig {

	private final JwtTokenConfig jwtTokenConfig;

	private SecretKey accessKey;
	private SecretKey refreshKey;

	private long accessExp;
	private long refreshExp;

	@PostConstruct
	public void init() {
		var accessProps = jwtTokenConfig.get(TokenType.ACCESS);
		this.accessKey = Keys.hmacShaKeyFor(accessProps.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.accessExp = accessProps.getExpirationSeconds() * 1000L;

		var refreshProps = jwtTokenConfig.get(TokenType.REFRESH);
		this.refreshKey = Keys.hmacShaKeyFor(refreshProps.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.refreshExp = refreshProps.getExpirationSeconds() * 1000L;
	}


}
