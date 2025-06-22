package src.global.security.jwt.service;

import java.util.Date;
import java.time.Duration;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import lombok.RequiredArgsConstructor;

import jakarta.annotation.PostConstruct;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import src.global.base.constant.ErrorCode;
import src.global.security.jwt.util.JwtUtil;
import src.global.security.jwt.dto.JwtRequest;
import src.global.security.jwt.dto.JwtResponse;
import src.global.base.exception.GlobalException;
import src.global.security.config.JwtTokenConfig;
import src.global.security.jwt.constant.TokenType;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private SecretKey accessSecretKey;
	private SecretKey refreshSecretKey;
	private long accessExpirationMillis;
	private long refreshExpirationMillis;

	private final JwtTokenConfig jwtTokenConfig;

	private final RedisTemplate<String, Object> redisTemplate;

	private static final String ACCESS_PREFIX = "access_token:";
	private static final String REFRESH_PREFIX = "refresh_token:";

	/**
	 * 초기화
	 */
	@PostConstruct
	public void init() {
		JwtTokenConfig.TokenProperties accessProps = jwtTokenConfig.get(TokenType.ACCESS);
		this.accessSecretKey = Keys.hmacShaKeyFor(accessProps.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.accessExpirationMillis = accessProps.getExpirationSeconds() * 1000L;

		JwtTokenConfig.TokenProperties refreshProps = jwtTokenConfig.get(TokenType.REFRESH);
		this.refreshSecretKey = Keys.hmacShaKeyFor(refreshProps.getSecretKey().getBytes(StandardCharsets.UTF_8));
		this.refreshExpirationMillis = refreshProps.getExpirationSeconds() * 1000L;
	}

	private String generateTokenFor(String userId, TokenType type, SecretKey key, long expirationMillis, Date now) {
		JwtUtil.validateToken(userId, type);
		Date expiry = new Date(now.getTime() + expirationMillis);
		JwtTokenConfig.TokenProperties props = jwtTokenConfig.get(type);
		SignatureAlgorithm algorithm = JwtUtil.getSignatureAlgorithm(props.getAlgorithm());
		JwtRequest.JwtTokenRequest request = JwtRequest.JwtTokenRequest.of(userId, props, now, expiry);
		return JwtUtil.generateToken(request, key, algorithm);
	}

	/**211
	 * Token용 키 생성
	 * @param userId // 해당 유저의 인증 아이디
	 * @param type // 요구하는 토큰 타입 (ACCESS, REFRESH)
	 * @return String jwt
	 * @throws GlobalException // 토큰 타입 에러
	 */
	@Override
	@Transactional(readOnly = true)
	public JwtResponse.JwtCreateTokenResponse createTokenPair(String userId, TokenType type) {
		Date now = new Date();
		String accessToken = generateTokenFor(userId, TokenType.ACCESS, accessSecretKey, accessExpirationMillis, now);
		String refreshToken = generateTokenFor(userId, TokenType.REFRESH, refreshSecretKey, refreshExpirationMillis, now);
		return new JwtResponse.JwtCreateTokenResponse(accessToken, refreshToken);
	}

	@Override
	@Transactional(readOnly = true)
	public JwtResponse.JwtCreateAccessTokenResponse createAccessToken(String userId) {
		JwtUtil.validateToken(userId, TokenType.ACCESS);

		Date now = new Date();
		String accessToken = generateTokenFor(userId, TokenType.ACCESS, accessSecretKey, accessExpirationMillis, now);
		return new JwtResponse.JwtCreateAccessTokenResponse(accessToken);
	}

	@Override
	@Transactional(readOnly = true)
	public JwtResponse.JwtCreateRefreshTokenResponse createRefreshToken(String userId) {
		JwtUtil.validateToken(userId, TokenType.REFRESH);

		Date now = new Date();
		String refreshToken = generateTokenFor(userId, TokenType.REFRESH, refreshSecretKey, refreshExpirationMillis, now);
		return new JwtResponse.JwtCreateRefreshTokenResponse(refreshToken);
	}

	@Override
	@Transactional(readOnly = true)
	public String saveToken(String userId, String token, TokenType type) {
		if (type == TokenType.ACCESS) {
			redisTemplate.opsForValue().set(ACCESS_PREFIX + userId, token, Duration.ofMillis(accessExpirationMillis));
		} else if (type == TokenType.REFRESH) {
			redisTemplate.opsForValue().set(REFRESH_PREFIX + userId, token, Duration.ofMillis(refreshExpirationMillis));
		} else {
			throw new GlobalException(ErrorCode.INVALID_TOKEN_TYPE);
		}
		return token;
	}
}