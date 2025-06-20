package src.domain.user.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;

import src.domain.user.entity.User;
import src.global.security.auth.entity.Auth;
import src.domain.user.entity.UserSocial;
import src.domain.user.constant.UserSocialType;

public class UserResponse {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserCreateResponse {
		@NotNull(message = "id is required")
		private UUID id;

		@NotNull(message = "social_type is required")
		private UserSocialType userSocialType;

		public static UserResponse.UserCreateResponse from(User user, Auth auth,UserSocial userSocial) {
			return UserResponse.UserCreateResponse.builder()
				.id(user.getId())
				.id(auth.getId())
				.userSocialType(userSocial.getSocialType())
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserUpdateResponse {
		@NotNull(message = "id is required")
		private UUID id;

		@NotNull(message = "social_type is required")
		private UserSocialType userSocialType;

		public static UserResponse.UserUpdateResponse from(User user, Auth auth,UserSocial userSocial) {
			return UserResponse.UserUpdateResponse.builder()
				.id(user.getId())
				.id(auth.getId())
				.userSocialType(userSocial.getSocialType())
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserDeleteResponse  {
		@NotNull(message = "id is required")
		private UUID id;
		@NotNull(message = "isDeleted is required")
		private Boolean isDeleted;

		public static UserResponse.UserDeleteResponse from(User user) {
			return UserResponse.UserDeleteResponse.builder()
				.id(user.getId())
				.isDeleted(true)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserAuthResponse {
		@NotNull(message = "id is required")
		private UUID id;

		@NotNull(message = "token is required")
		private String token;

		public static UserResponse.UserAuthResponse from(Auth auth, String token) {
			return UserResponse.UserAuthResponse.builder()
				.id(auth.getId())
				.token(token)
				.build();
		}
	}

}
