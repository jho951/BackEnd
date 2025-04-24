package src.global.common.log;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import src.global.constant.log.LogLevel;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("@annotation(loggable)")
	public Object logMethodCall(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
		String methodName = joinPoint.getSignature().toShortString();
		Object[] args = joinPoint.getArgs();

		logByLevel(loggable.level(), "[START] " + methodName + " args=" + Arrays.toString(args));

		try {
			Object result = joinPoint.proceed();

			logByLevel(loggable.level(), "[END] " + methodName + " return=" + result);
			return result;

		} catch (Throwable throwable) {
			logByLevel(LogLevel.ERROR, "[EXCEPTION] " + methodName + " ex=" + throwable.getMessage());
			throw throwable;
		}
	}

	private void logByLevel(LogLevel level, String message) {
		switch (level) {
			case TRACE -> log.trace(message);
			case DEBUG -> log.debug(message);
			case INFO -> log.info(message);
			case WARN -> log.warn(message);
			case ERROR -> log.error(message);
		}
	}
}

