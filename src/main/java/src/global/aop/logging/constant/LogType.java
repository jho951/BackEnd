package src.global.aop.logging.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogType {
	API("api"),
	SYSTEM("system"),
	DEFAULT("default");

	private final String type;
}
