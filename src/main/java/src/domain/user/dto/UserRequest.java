package src.domain.user.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

import src.domain.user.entity.User;
import src.domain.user.entity.UserAuth;
import src.domain.user.entity.UserSocial;
import src.domain.user.constant.UserRole;
import src.domain.user.constant.UserStatus;
import src.domain.user.constant.UserSocialType;

public class UserRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserCreateRequest {
		@NotNull(message = "id is required")
		@Schema(description = "유저 식별 아이디", example = "0x123E4567E89B12D3A456426614174000")
		private UUID id;

		@NotBlank(message = "email is required")
		@Schema(description = "유저 이메일", example = "useremail@example.com")
		private String email;

		@NotBlank(message = "name is required")
		@Schema(description = "유저 이메일", example = "홍길동")
		private String name;

		@NotBlank(message = "password is required")
		@Schema(description = "유저 비밀번호", example = "test1234!")
		private String password;

		public User toUserEntity(){
			return User.builder()
				.email(this.email)
				.name(this.name)
				.role(UserRole.ROLE_USER)
				.status(UserStatus.ACTIVE)
				.build();
		}

		public UserAuth toAuthEntity(User user) {
			return UserAuth.builder()
				.user(user)
				.password(this.password)
				.build();
		}

		public UserSocial toSocialEntity(User user) {
			return UserSocial.builder()
				.user(user)
				.socialType(UserSocialType.DEFAULT)
				.build();
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserUpdateRequest {
		@NotNull(message = "id is required")
		@Schema(description = "유저 식별 아이디", example = "0x123E4567E89B12D3A456426614174000")
		private UUID id;

		@NotBlank(message = "email is required")
		@Schema(description = "유저 이메일", example = "useremail@example.com")
		private String email;

		@NotBlank(message = "name is required")
		@Schema(description = "유저 이메일", example = "홍길동")
		private String name;

		@NotBlank(message = "password is required")
		@Schema(description = "유저 비밀번호", example = "test1234!")
		private String password;

	}
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserAuthRequest {
		@NotBlank(message = "email is required")
		@Schema(description = "유저 이메일", example = "useremail@example.com")
		private String email;

		@NotBlank(message = "password is required")
		@Schema(description = "유저 비밀번호", example = "test1234!")
		private String password;

		public UserAuth toAuthEntity(User user) {
			return UserAuth.builder()
				.user(user)
				.password(this.password)
				.build();
		}
	}
}
