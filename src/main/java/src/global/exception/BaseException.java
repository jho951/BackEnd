package src.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import src.global.constant.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
	private final ErrorCode code;
}
