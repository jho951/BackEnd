package src.global.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import src.global.constant.log.LogLevel;
import src.global.constant.log.LogValue;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
	LogLevel level() default LogLevel.DEBUG;
	LogValue value() default LogValue.DEFAULT;
}
