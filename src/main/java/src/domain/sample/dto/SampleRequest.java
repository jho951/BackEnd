package src.domain.sample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotEmpty;

import src.domain.sample.entity.Sample;

public class SampleRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SampleCreateRequest {
		@NotBlank(message = "content is required")
		@Schema(description = "샘플 내용", example = "sample content")
		private String contents;

		public Sample toCreateEntity() {
			return Sample.builder()
				.contents(this.contents)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SampleUpdateRequest {
		@NotNull(message = "id is required")
		@Schema(description = "샘플 도메인의 식별 아이디", example = "1")
		private Long id;

		@NotNull(message = "name is required")
		@Schema(description = "샘플 내용", example = "sample content")
		private String contents;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SampleReadRequest {
		@NotBlank(message = "id is required")
		@Schema(description = "샘플 도메인의 식별 아이디", example = "1")
		private Long id;

		public Sample toReadEntity() {
			return Sample.builder()
				.id(this.id)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SampleDeleteRequest {
		@NotEmpty(message = "id is required")
		@Schema(description = "샘플 도메인의 식별 아이디", example = "1")
		private Long id;

		public Sample toDeleteEntity() {
			return Sample.builder()
				.id(this.id)
				.build();
		}
	}
}