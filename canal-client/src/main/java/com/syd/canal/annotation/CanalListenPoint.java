package com.syd.canal.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 以注解方式使用的canal事件监听器，需要配合ListenPoint及其子注解配合使用
 * <p>inject the present class to the spring context
 * as a listener of the canal event
 *
 * @author jigua
 * @date 2018/3/19
 * @see ListenPoint
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CanalListenPoint {

    @AliasFor(annotation = Component.class)
    String value() default "";

}

