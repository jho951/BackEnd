package src.global.common.dto;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import src.global.constant.code.ErrorCode;
import src.global.constant.code.SuccessCode;

@Getter
public final class BaseResponseWrapper<T> {
	@Schema(description = "HTTP 상태 코드", example = "200")
	private final HttpStatus httpStatus;
	@Schema(description = "성공 여부", example = "true")
	private final Boolean isSuccess;
	@Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
	private final String message;
	@Schema(description = "비즈니스 코드", example = "1000")
	private final int code;
	@Schema(description = "응답 데이터")
	private final T data;

	public BaseResponseWrapper(HttpStatus httpStatus, Boolean isSuccess, String message, int code, T data) {
		if (httpStatus == null || message == null) {
			throw new IllegalArgumentException("HTTP 상태와 메시지는 null일 수 없습니다.");
		}
		this.httpStatus = httpStatus;
		this.isSuccess = isSuccess;
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public static <T> BaseResponseWrapper<T> ok(SuccessCode successCode, T data) {
		if (successCode == null || data == null) {
			throw new IllegalArgumentException("HTTP 상태와 메시지는 null일 수 없습니다.");
		}
		return new BaseResponseWrapper<>(
			successCode.getHttpStatus(),
			successCode.getIsSuccess(),
			successCode.getMessage(),
			successCode.getCode(),
			data
		);
	}

	public static BaseResponseWrapper<Void> ok() {
		return new BaseResponseWrapper<>(
			SuccessCode.SUCCESS.getHttpStatus(),
			SuccessCode.SUCCESS.getIsSuccess(),
			SuccessCode.SUCCESS.getMessage(),
			SuccessCode.SUCCESS.getCode(),
			null
		);
	}

	public static BaseResponseWrapper<Void> fail(ErrorCode errorCode) {
		if (errorCode == null) {
			throw new IllegalArgumentException("HTTP 상태와 메시지는 null일 수 없습니다.");
		}
		return new BaseResponseWrapper<>(
			errorCode.getHttpStatus(),
			errorCode.getIsSuccess(),
			errorCode.getMessage(),
			errorCode.getCode(),
			null
		);
	}

	public static BaseResponseWrapper<Void> fail(ErrorCode errorCode, String message) {
		if (errorCode == null || message == null) {
			throw new IllegalArgumentException("HTTP 상태와 메시지는 null일 수 없습니다.");
		}
		return new BaseResponseWrapper<>(
			errorCode.getHttpStatus(),
			errorCode.getIsSuccess(),
			message,
			errorCode.getCode(),
			null
		);
	}
}
