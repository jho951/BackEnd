package src.global.constant.log;

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