package src.global.security.jwt.service;

import src.global.security.jwt.constant.TokenType;
import src.global.security.jwt.dto.JwtResponse;

public interface TokenService {
	JwtResponse.JwtCreateTokenResponse createTokenPair(String userId, TokenType tokenType);
	JwtResponse.JwtCreateAccessTokenResponse createAccessToken(String userId);
	JwtResponse.JwtCreateRefreshTokenResponse createRefreshToken(String userId);
	String saveToken(String userId, String token, TokenType type);
}
