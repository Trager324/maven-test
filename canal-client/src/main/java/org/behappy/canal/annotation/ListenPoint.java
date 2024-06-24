package org.behappy.canal.annotation;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.lang.annotation.*;

/**
 * 需要作用于CanalListenPoint标注的类上，可以更细化为增、删、改三个监听点
 * <p>每个以该注解标注的方法会被封装为一个监听器注册到事件循环中
 * <p>used to indicate that method(or methods) is(are) the candidate of the
 * canal event distributor
 *
 * @author jigua
 * @date 2018/3/19
 * @see CanalListenPoint
 * @see InsertListenPoint
 * @see UpdateListenPoint
 * @see DeleteListenPoint
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ListenPoint {

    /**
     * canal destination default for all
     *
     * @return canal destination
     */
    String destination() default "";

    /**
     * database schema which you are concentrate on default for all
     *
     * @return canal destination
     */
    String[] schema() default {};

    /**
     * tables which you are concentrate on default for all
     *
     * @return canal destination
     */
    String[] table() default {};

    /**
     * canal event type default for all
     *
     * @return canal event type
     */
    CanalEntry.EventType[] eventType() default {};

}

