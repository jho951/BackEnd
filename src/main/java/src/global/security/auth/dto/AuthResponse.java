package src.global.security.auth.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import src.global.security.auth.entity.Auth;

public class AuthResponse {
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AuthLoginResponse {
		@NotNull(message = "id is required")
		@Schema(description = "유저 식별 아이디", example = "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d")
		private UUID id;

		@NotNull(message = "token is required")
		@Schema(description = "로그인 토큰", example = "r4nd0mStr1ngThatIsAtLeast32Characters")
		private String token;

		public static AuthResponse.AuthLoginResponse from(Auth auth, String token) {
			return AuthResponse.AuthLoginResponse.builder()
				.id(auth.getId())
				.token(token)
				.build();
		}
	}
}
