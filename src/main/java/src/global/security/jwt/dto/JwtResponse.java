package src.global.security.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;


public class JwtResponse {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class JwtCreateTokenResponse{
		private final String accessToken;
		private final String refreshToken;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class JwtCreateAccessTokenResponse{
		private final String accessToken;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class JwtCreateRefreshTokenResponse{
		private final String accessToken;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class JwtSaveTokenResponse {
		private String token;
		private String tokenType;
		private long expiresInMillis;
	}


}
