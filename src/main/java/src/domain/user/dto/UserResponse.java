package src.domain.user.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import src.domain.user.entity.User;
import src.domain.user.entity.UserAuth;
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

		public static UserResponse.UserCreateResponse from(User user,UserAuth userAuth,UserSocial userSocial) {
			return UserResponse.UserCreateResponse.builder()
				.id(user.getId())
				.id(userAuth.getId())
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

		public static UserResponse.UserUpdateResponse from(User user,UserAuth userAuth,UserSocial userSocial) {
			return UserResponse.UserUpdateResponse.builder()
				.id(user.getId())
				.id(userAuth.getId())
				.userSocialType(userSocial.getSocialType())
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserDeleteResponse  {
		@NotEmpty(message = "id is required")
		private UUID id;
		@NotEmpty(message = "isDeleted is required")
		private Boolean isDeleted;

		public static UserResponse.UserDeleteResponse from(User user) {
			return UserResponse.UserDeleteResponse.builder()
				.id(user.getId())
				.isDeleted(true)
				.build();
		}
	}
}
