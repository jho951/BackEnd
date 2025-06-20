package src.global.security.auth.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

import src.global.security.auth.entity.Auth;

public class AuthResponse {
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AuthLoginResponse {
		@NotNull(message = "id is required")
		private UUID id;

		@NotNull(message = "token is required")
		private String token;

		public static AuthResponse.AuthLoginResponse from(Auth auth, String token) {
			return AuthResponse.AuthLoginResponse.builder()
				.id(auth.getId())
				.token(token)
				.build();
		}
	}
}
