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


}
