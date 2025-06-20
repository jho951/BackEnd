package src.global.logging.util;

import java.util.logging.Logger;

import org.slf4j.MDC;

import src.global.logging.constant.LogLevel;

/**
 * @param level 로그 레벨
 * @param message 로깅에 적힐 메시지
 */
public class LogUtil {
	public static void logByLevel(Logger logger, LogLevel level, String message) {
		switch (level) {
			case TRACE -> logger.trace(message);
			case DEBUG -> logger.debug(message);
			case INFO -> logger.info(message);
			case WARN -> logger.warn(message);
			case ERROR -> logger.error(message);
		}
	}
	public class LogContextManager {
		public static void setContext(String key, String value) {
			MDC.put(key, value);
		}

		public static void clearContext(String key) {
			MDC.remove(key);
		}
	}
}
