package org.behappy.common.annotation;

import org.behappy.common.aspect.TimerAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * aop计时注解
 *
 * @author songyide
 * @see TimerAspect
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {
    /**
     * 计时信息
     */
    String value() default "";
}
