package src.global.exception;

import lombok.Getter;
import src.global.constant.code.ErrorCode;

@Getter
public class GlobalException extends RuntimeException {

	private final ErrorCode errorCode;

	public GlobalException(final ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}