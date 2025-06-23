package src.global.security.jwt.service;

import java.util.Date;
import java.time.Duration;

import javax.crypto.SecretKey;

import lombok.RequiredArgsConstructor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import src.global.base.constant.ErrorCode;
import src.global.security.jwt.util.JwtUtil;
import src.global.security.jwt.dto.JwtRequest;
import src.global.security.jwt.dto.JwtResponse;
import src.global.base.exception.GlobalException;
import src.global.security.jwt.constant.TokenType;
import src.global.security.jwt.config.JwtKeyConfig;
import src.global.security.jwt.config.JwtTokenConfig;
import src.global.security.user.CustomUserDetailsService;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final JwtTokenConfig jwtTokenConfig;
	private final JwtKeyConfig jwtKeyConfig;

	private final RedisTemplate<String, Object> redisTemplate;
	private final CustomUserDetailsService customUserDetailsService;

	private static final String ACCESS_PREFIX = "access_token:";
	private static final String REFRESH_PREFIX = "refresh_token:";


	/**
	 * 가나
	 * @param userId
	 * @param type
	 * @param key
	 * @param expirationMillis
	 * @param now
	 * @return rk
	 */
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
		String accessToken = generateTokenFor(userId, TokenType.ACCESS, jwtKeyConfig.getAccessKey(), jwtKeyConfig.getAccessExp(), now);
		String refreshToken = generateTokenFor(userId, TokenType.REFRESH, jwtKeyConfig.getRefreshKey(), jwtKeyConfig.getRefreshExp(), now);
		return new JwtResponse.JwtCreateTokenResponse(accessToken, refreshToken);
	}

	@Override
	@Transactional(readOnly = true)
	public JwtResponse.JwtCreateAccessTokenResponse createAccessToken(String userId) {
		JwtUtil.validateToken(userId, TokenType.ACCESS);

		Date now = new Date();
		String accessToken = generateTokenFor(userId, TokenType.ACCESS, jwtKeyConfig.getAccessKey(), jwtKeyConfig.getAccessExp(), now);
		return new JwtResponse.JwtCreateAccessTokenResponse(accessToken);
	}

	@Override
	@Transactional(readOnly = true)
	public JwtResponse.JwtCreateRefreshTokenResponse createRefreshToken(String userId) {
		JwtUtil.validateToken(userId, TokenType.REFRESH);

		Date now = new Date();
		String refreshToken = generateTokenFor(userId, TokenType.REFRESH, jwtKeyConfig.getRefreshKey(), jwtKeyConfig.getRefreshExp(), now);
		return new JwtResponse.JwtCreateRefreshTokenResponse(refreshToken);
	}

	@Override
	@Transactional(readOnly = true)
	public JwtResponse.JwtSaveTokenResponse saveToken(String userId, String token, TokenType type) {
		long expirationTime;

		if (type == TokenType.ACCESS) {
			expirationTime = jwtKeyConfig.getAccessExp();
			redisTemplate.opsForValue().set(ACCESS_PREFIX + userId, token, Duration.ofMillis(expirationTime));
		} else if (type == TokenType.REFRESH) {
			expirationTime = jwtKeyConfig.getRefreshExp();
			redisTemplate.opsForValue().set(REFRESH_PREFIX + userId, token, Duration.ofMillis(expirationTime));
		} else {
			throw new GlobalException(ErrorCode.INVALID_TOKEN_TYPE);
		}

		return new JwtResponse.JwtSaveTokenResponse(token, type.name(), expirationTime);
	}

	/**
	 * AccessToken 제거
	 * @param userId // 유저 식별 아이디
	 */
	@Transactional
	@Override
	public void deleteAccessToken(String userId) {
		redisTemplate.delete(ACCESS_PREFIX + userId);
	}

	/**
	 * RefreshToken 제거
	 * @param userId // 유저 식별 아이디
	 */
	@Transactional
	@Override
	public void deleteRefreshToken(String userId) {
		redisTemplate.delete(REFRESH_PREFIX + userId);
	}

	/**
	 * ✅ 토큰 유효성 검증
 	 */
	@Transactional
	@Override
	public boolean validateRefreshToken(String token) {
		try {
			Claims claims = JwtUtil.parseToken(token, jwtKeyConfig.getRefreshKey());
			String userId = claims.getSubject();
			String savedToken = (String) redisTemplate.opsForValue().get(REFRESH_PREFIX + userId);
			return token.equals(savedToken);
		} catch (Exception e) {
			return false;
		}
	}

	// ✅ 인증 객체 생성
	public Authentication getAuthentication(String token, SecretKey secretKey) {
		String userId = JwtUtil.extractUserId(token, secretKey);
		var userDetails = customUserDetailsService.loadUserByUsername(userId);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}