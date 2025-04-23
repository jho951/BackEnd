package src.domain.user.dto;

import static src.domain.user.constant.UserRole.*;
import static src.domain.user.constant.UserSocial.*;
import static src.domain.user.constant.UserStatus.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotEmpty;

import src.domain.user.constant.UserRole;
import src.domain.user.constant.UserSocial;
import src.domain.user.constant.UserStatus;
import src.domain.user.entity.User;
import src.domain.user.entity.UserAuth;

public class UserResponse {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserCreateResponse {
		@NotEmpty(message = "id is required")
		private Long id;

		public static UserResponse.UserCreateResponse from(User user, UserAuth userAuth) {
			return UserResponse.UserCreateResponse.builder()
				.id(user.getId())
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserDeleteResponse  {
		@NotEmpty(message = "id is required")
		private Long id;
		@NotEmpty(message = "isDeleted is required")
		private Boolean isDeleted;

		public static UserResponse.UserDeleteResponse from(User user, UserAuth userAuth) {
			return UserResponse.UserDeleteResponse.builder()
				.id(user.getId())
				.isDeleted(true)
				.build();
		}
	}
}
