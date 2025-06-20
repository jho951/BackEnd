package src.global.security.jwt.service;

public interface AccessTokenService {
	String createAccessToken(String userId);
}
