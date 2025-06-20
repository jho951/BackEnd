package src.global.security.jwt.service;

public interface RefreshTokenService {
	String createAndSaveRefreshToken(String userId);
	boolean validateRefreshToken(String token);
	void deleteRefreshToken(String userId);
}
