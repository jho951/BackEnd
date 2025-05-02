package src.global.common.log;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

import src.global.constant.log.LogLevel;



@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	// Elasticsearch 관련 설정을 추가
	@Value("${elasticsearch.index.prefix:spring-logs}")
	private String logIndexPrefix;

	@Around("@annotation(loggable)")
	public Object logMethodCall(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
		String methodName = joinPoint.getSignature().toShortString();
		Object[] args = joinPoint.getArgs();

		// MDC에 log_type 설정 (예: "api" 또는 "system")
		String logType = String.valueOf(loggable.value()); // loggable이 지정하는 타입 (예: "api", "system")
		MDC.put("log_type", logType);  // MDC에 log_type을 넣음

		// 로그 레벨에 맞는 로깅
		logByLevel(loggable.level(), "[START] " + methodName + " args=" + Arrays.toString(args));

		try {
			Object result = joinPoint.proceed();

			logByLevel(loggable.level(), "[END] " + methodName + " return=" + result);
			return result;

		} catch (Throwable throwable) {
			logByLevel(LogLevel.ERROR, "[EXCEPTION] " + methodName + " ex=" + throwable.getMessage());
			throw throwable;
		} finally {
			MDC.remove("log_type");  // MDC에서 log_type 제거
		}
	}

	private void logByLevel(LogLevel level, String message) {
		String index = logIndexPrefix + "-" + level.name().toLowerCase();
		// 로그 메시지에 맞게 logstash와 연동된 로그를 출력 (예시: Elasticsearch index별로 저장)
		switch (level) {
			case TRACE -> log.trace(message);
			case DEBUG -> log.debug(message);
			case INFO -> log.info(message);
			case WARN -> log.warn(message);
			case ERROR -> log.error(message);
		}

		// 로그를 Elasticsearch에 저장하는 추가적인 로직을 추가할 수 있음
		// 예: Elasticsearch로 전송되는 로그 레벨에 맞춰 인덱스에 기록
		// 이 부분은 애플리케이션에서 Elasticsearch로 로깅 데이터를 전송하는 로직을 추가하는 부분에 의존함
	}
}
