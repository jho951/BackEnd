package src.domain.user.dto;


import static src.domain.user.constant.UserStatus.*;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import src.domain.user.constant.UserRole;
import src.domain.user.constant.UserSocial;
import src.domain.user.constant.UserStatus;
import src.domain.user.entity.User;
import src.domain.user.entity.UserAuth;

public class UserRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserCreateRequest {

		@NotBlank(message = "email is required")
		private String email;

		@NotBlank(message = "name is required")
		private String name;

		@NotBlank(message = "password is required")
		private String password;

		@NotNull(message = "role is required")
		private UserRole role;

		@NotNull(message = "userStatus is required")
		private UserStatus userStatus;

		@NotNull(message = "socialType is required")
		private UserSocial socialType;

		public User toCreateEntity(){
			return User.builder()
				.email(this.email)
				.name(this.name)
				.password(this.password)
				.role(this.role)
				.status(ACTIVE)
				.build();
		}

		public UserAuth toCreateUserAuthEntity(User user){
			return UserAuth.builder()
				.user(user)
				.socialType(this.socialType)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserDeleteRequest {
		@NotNull(message = "id is required")
		@Schema(description = "유저 도메인의 식별 아이디", example = "1ade2df3fs2e12r")
		private String id;

		@NotNull(message = "isDeleted is required")
		@Column(name ="isDeleted", nullable = false)
		@Schema(description = "유저 삭제 여부", example = "false")
		private Boolean isDeleted;

		public UserAuth toDeleteEntity(UserAuth userAuth) {
			return UserAuth.builder()
				.id(userAuth.getId())
				.isDeleted(true)
				.build();
		}
	}
}
