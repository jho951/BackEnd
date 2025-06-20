package src.global.aop.logging.context;

import org.slf4j.MDC;

public class MDCManager {
	public static void put(String key, String value) {
		MDC.put(key, value);
	}

	public static void remove(String key) {
		MDC.remove(key);
	}
}