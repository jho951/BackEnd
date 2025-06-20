package src.global.aop.logging.aspect;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import src.global.aop.logging.util.LoggingUtil;
import src.global.aop.logging.constant.LogLevel;
import src.global.aop.logging.context.MDCManager;
import src.global.aop.logging.annotation.Loggable;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
	/**
	 * @param joinPoint 대상 메서드의 메타정보와 인자에 접근
	 * @param loggable 어노테이션으로부터 설정값을 직접 꺼냄
	 * @return result 해당 로그
	 * @throws Throwable
	 */
	// @Loggable 어노테이션이 붙은 메서드 실행 전후에 실행
	@Around("@annotation(loggable)")
	public Object logMethodCall(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
		String methodName = joinPoint.getSignature().toShortString();
		Object[] args = joinPoint.getArgs();

		MDCManager.put("log_type", loggable.type().name());
		LoggingUtil.logByLevel(loggable.level(), "[START] " + methodName + " args=" + Arrays.toString(args));

		try {
			Object result = joinPoint.proceed();
			LoggingUtil.logByLevel(loggable.level(), "[END] " + methodName + " return=" + result);
			return result;
		} catch (Throwable throwable) {
			LoggingUtil.logByLevel(LogLevel.ERROR, "[EXCEPTION] " + methodName + " ex=" + throwable.getMessage());
			throw throwable;
		} finally {
			MDCManager.remove("log_type");
		}
	}

}
