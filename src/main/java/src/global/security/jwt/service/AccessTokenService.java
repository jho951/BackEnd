package src.global.security.jwt.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AccessTokenService {
	String createAccessToken(String userId);
	String resolveToken(HttpServletRequest request);
	boolean validateToken(String token);
}
