package src.global.constant.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogType {
	API("api"),
	SYSTEM("syetem"),
	DEFAULT("default");

	private final String type;
}
