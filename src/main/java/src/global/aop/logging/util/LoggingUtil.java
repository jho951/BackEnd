package src.global.aop.logging.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.global.aop.logging.constant.LogLevel;

public class LoggingUtil {
	private static final Logger log = LoggerFactory.getLogger(LoggingUtil.class);

	public static void logByLevel(LogLevel level, String message) {
		switch (level) {
			case TRACE -> log.trace(message);
			case DEBUG -> log.debug(message);
			case INFO  -> log.info(message);
			case WARN  -> log.warn(message);
			case ERROR -> log.error(message);
		}
	}
}
