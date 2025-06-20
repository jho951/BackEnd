package src.global.logging.exception;

public class ExceptionLogging {
	public static void log(Logger logger, String methodName, Throwable e) {
		logger.error("[EXCEPTION] {} ex={}", methodName, e.getMessage(), e);
	}
}
