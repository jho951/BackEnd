package src.global.security.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import src.domain.user.entity.User;
import src.global.security.auth.entity.Auth;

public class AuthRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AuthLoginRequest {
		@NotBlank(message = "email is required")
		@Schema(description = "유저 이메일", example = "useremail@example.com")
		private String email;

		@NotBlank(message = "password is required")
		@Schema(description = "유저 비밀번호", example = "test1234!")
		private String password;

		public Auth toAuthEntity(User user) {
			return Auth.builder()
				.user(user)
				.password(this.password)
				.build();
		}
	}
}
