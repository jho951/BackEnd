package src.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import src.domain.product.entity.Product;
import src.domain.product.constant.ProductType;

public class ProductRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductCreateRequest {
		@NotBlank(message = "title is required")
		@Schema(description = "상품 제목", example = "product title")
		private String title;

		@NotBlank(message = "content is required")
		@Schema(description = "상품 내용", example = "product content")
		private String contents;

		@NotNull(message = "price is required")
		@Schema(description = "상품 가격", example = "10000")
		private Double price;

		@NotNull(message = "productType is required")
		@Schema(description = "상품 타입", example = "MAN")
		private ProductType productType;


		public Product toCreateEntity() {
			return Product.builder()
				.title(this.title)
				.contents(this.contents)
				.price(this.price)
				.productType(this.productType)
				.createdBy("user")
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductUpdateRequest {
		@NotBlank(message = "id is required")
		@Schema(description = "상품 아이디", example = "1")
		private Long id;

		@NotBlank(message = "title is required")
		@Schema(description = "상품 제목", example = "product title")
		private String title;

		@NotBlank(message = "content is required")
		@Schema(description = "상품 내용", example = "product content")
		private String contents;

		@NotNull(message = "price is required")
		@Schema(description = "상품 가격", example = "10000")
		private Double price;

		@NotNull(message = "productType is required")
		@Schema(description = "상품 타입", example = "MAN")
		private ProductType productType;

		@NotNull(message = "createdBy is required")
		@Schema(description = "상품 생성자", example = "user")
		private String createdBy;

		@NotNull(message = "modified_by is required")
		@Schema(description = "상품 수정자", example = "user")
		private String modifiedBy;

		@NotNull(message = "isDeleted is required")
		@Schema(description = "상품 삭제 여부", example = "false")
		private Boolean isDeleted;
	}
}
