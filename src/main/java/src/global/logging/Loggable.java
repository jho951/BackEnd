package src.global.logging;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import src.global.logging.constant.LogType;
import src.global.logging.constant.LogLevel;

// 메서드나 클래스에 붙입니다.
@Target({ElementType.METHOD, ElementType.TYPE})
// 런타임에 유지
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
	LogLevel level() default LogLevel.DEBUG;
	LogType type() default LogType.DEFAULT;
}
