package src.global.constant.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

	// Sample
	FIND_SAMPLE_DATA_SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "샘플 데이터를 찾았습니다."),
	FIND_SAMPLE_DATA_LIST_SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "샘플 전체 데이터를 찾았습니다."),
	SAVE_SAMPLE_DATA_SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "샘플 데이터를 저장하였습니다."),

	// Product
	FIND_PRODUCT_DATA_SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "상품 데이터를 찾았습니다."),
	FIND_PRODUCT_DATA_LIST_SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "상품 전체 데이터를 찾았습니다."),
	SAVE_PRODUCT_DATA_SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "상품 데이터를 저장하였습니다."),

	// Common
	SUCCESS(HttpStatus.OK, Boolean.TRUE, 200, "요청 응답을 성공하였습니다.");

	private final HttpStatus httpStatus;
	private final Boolean isSuccess;
	private final int code;
	private final String message;
}