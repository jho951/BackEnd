package src.domain.product.controller;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.global.common.log.Loggable;
import src.global.constant.log.LogType;
import src.global.constant.log.LogLevel;
import src.global.constant.code.SuccessCode;
import src.global.common.dto.GlobalResponse;
import src.domain.product.dto.ProductRequest;
import src.domain.product.dto.ProductResponse;
import src.domain.product.service.ProductService;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "This is a product controller")
public class ProductController {
	private final ProductService productService;

	/**
	 * prodcut 생성
	 *
	 * @param dto createRequestDto
	 * @return SuccessCode, response
	 */
	@PostMapping("/create")
	@Operation(summary = "Create", description = "product 생성")
	@ApiResponse(responseCode = "200", description = "성공적으로 생성하였습니다.")
	@Loggable(level = LogLevel.INFO, type = LogType.API)
	public GlobalResponse<ProductResponse.ProductCreateResponse> create(@RequestBody @Valid ProductRequest.ProductCreateRequest dto) {
		ProductResponse.ProductCreateResponse response = productService.create(dto);
		return GlobalResponse.ok(SuccessCode.SUCCESS, response);
	}
	/**
	 * prodcut 수정
	 *
	 * @param dto createRequestDto
	 * @return SuccessCode, response
	 */
	@PostMapping("/update")
	@Operation(summary = "Update", description = "product 수정")
	@ApiResponse(responseCode = "200", description = "성공적으로 수정하였습니다.")
	@Loggable(level = LogLevel.INFO, type = LogType.API)
	public GlobalResponse<ProductResponse.ProductUpdateResponse> update(@RequestBody @Valid ProductRequest.ProductUpdateRequest dto) {
		ProductResponse.ProductUpdateResponse response = productService.update(dto);
		return GlobalResponse.ok(SuccessCode.SUCCESS, response);
	}
}
