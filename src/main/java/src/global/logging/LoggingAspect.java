package src.global.logging;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import src.global.logging.constant.LogLevel;

// AOP로 인식하고 bean에 등록
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
	private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * @param level 로그 레벨
	 * @param message 로깅에 적힐 메시지
	 */
	private void logByLevel(LogLevel level, String message) {
		// 로그 레벨에 따라 Lombok 기반 Logger를 통해 메시지 출력
		switch (level) {
			case TRACE -> log.trace(message);
			case DEBUG -> log.debug(message);
			case INFO -> log.info(message);
			case WARN -> log.warn(message);
			case ERROR -> log.error(message);
		}
	}

	/**
	 * @param joinPoint 대상 메서드의 메타정보와 인자에 접근
	 * @param loggable 어노테이션으로부터 설정값을 직접 꺼냄
	 * @return result 해당 로그
	 * @throws Throwable
	 */
	// @Loggable 어노테이션이 붙은 메서드 실행 전후에 실행
	@Around("@annotation(loggable)")
	public Object logMethodCall(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
		// 메서드의 졍보을 추출 ex) SampleService.create(..)
		String methodName = joinPoint.getSignature().toShortString();
		// 대상 메서드의 인자 파라미터를 가져옴
		Object[] args = joinPoint.getArgs();

		// @Loggable에 설정한 type을 문자열로 변환 ex) "log", "system"
		String logType = String.valueOf(loggable.type());
		// MDC에 log_type이라는 key로 값을 저장
		MDC.put("log_type", logType);

		// @Loggable에 설정에 레벨에 맞게 START 로그를 출력
		logByLevel(loggable.level(), "[START] " + methodName + " args=" + Arrays.toString(args));

		try {
			// 비즈니스 로직 메서드가 여기서 실행됨
			Object result = joinPoint.proceed();

			// 메서드 실행이 끝난 뒤 결과 값을 로깅
			logByLevel(loggable.level(), "[END] " + methodName + " return=" + result);
			return result;

		} catch (Throwable throwable) {
			// 예외 발생 시 ERROR 레벨로 예외 메시지를 로깅 후 예외를 던짐
			logByLevel(LogLevel.ERROR, "[EXCEPTION] " + methodName + " ex=" + throwable.getMessage());
			throw throwable;
		} finally {
			// MDC는 ThreadLocal 기반이므로 메모리 누수 방지를 위해 제거를 해야합니다.
			MDC.remove("log_type");
		}
	}
}
