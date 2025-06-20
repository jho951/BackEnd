package src.global.aop.logging.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogLevel {
	TRACE("trace"),
	DEBUG("debug"),
	INFO("info"),
	WARN("warn"),
	ERROR("error");

	private final String level;
}