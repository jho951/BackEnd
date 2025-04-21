package src.global.constant.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogLevel {
	TRACE,
	DEBUG,
	INFO,
	WARN,
	ERROR;
}