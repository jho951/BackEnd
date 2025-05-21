package src.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import src.domain.product.entity.Product;

public class ProductResponse {

	@Getter
	@SuperBuilder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductCreateResponse  {
		@NotNull(message = "id is required")
		@Schema(description = "상품 아이디", example = "1")
		private Long id;

		public static ProductCreateResponse from(Product product) {
			return ProductCreateResponse.builder()
				.id(product.getId())
				.build();
		}
	}
	@Getter
	@SuperBuilder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductUpdateResponse{
		@NotNull(message = "id is required")
		@Schema(description = "상품 아이디", example = "1")
		private Long id;

		@NotNull(message = "version is required")
		@Schema(description = "상품 버전", example = "1")
		private int version;

		@NotNull(message = "modified_by is required")
		@Schema(description = "수정한 사람", example = "user")
		private String modifiedBy;

		public static ProductUpdateResponse from(Product product) {
			return ProductUpdateResponse.builder()
				.id(product.getId())
				.version(product.getVersion())
				.modifiedBy(product.getCreatedBy())
				.build();
		}

	}

}
