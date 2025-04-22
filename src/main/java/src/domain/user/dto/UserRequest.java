package src.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import src.domain.user.entity.User;

public class UserRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UserDeleteRequest {
		@NotEmpty(message = "id is required")
		@Schema(description = "유저 도메인의 식별 아이디", example = "1")
		private Long id;

		@Column(name ="isDeleted", nullable = false)
		@Schema(description = "유저 삭제 여부", example = "false")
		private Boolean isDeleted;

		public User toDeleteEntity(User user) {
			return User.builder()
				.id(user.getId())
				.isDeleted(true)
				.build();
		}
	}
}
