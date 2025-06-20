package src.global.aop.logging.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import src.global.aop.logging.constant.LogType;
import src.global.aop.logging.constant.LogLevel;

/**
 * 어노테이션 기반 AOP를 위한 핵심 포인트입니다.
 * 특정 메서드나 클래스에 @Loggable을 붙이면 AOP가 작동합니다.
 * 런타임에 유지
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
	LogLevel level() default LogLevel.DEBUG;
	LogType type() default LogType.DEFAULT;
}
