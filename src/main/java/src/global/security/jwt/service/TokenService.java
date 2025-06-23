package src.global.security.jwt.service;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import src.global.security.jwt.constant.TokenType;
import src.global.security.jwt.dto.JwtResponse;

public interface TokenService {
	JwtResponse.JwtCreateTokenResponse createTokenPair(String userId, TokenType tokenType);

	JwtResponse.JwtCreateAccessTokenResponse createAccessToken(String userId);

	JwtResponse.JwtCreateRefreshTokenResponse createRefreshToken(String userId);

	JwtResponse.JwtSaveTokenResponse saveToken(String userId, String token, TokenType type);

	void deleteAccessToken(String userId);

	void deleteRefreshToken(String userId);

	boolean validateRefreshToken(String token);

	Authentication getAuthentication(String token, SecretKey secretKey);
}
