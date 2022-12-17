package com.syd.common.aspect;


import com.syd.common.annotation.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


/**
 * 计时aop逻辑
 *
 * @author songyide
 */
@Aspect
@Component
@Slf4j
public class TimerAspect {

    @Around("@within(timer) || @annotation(timer)")
    public Object around(ProceedingJoinPoint pjp, Timer timer) throws Throwable {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        if (timer == null) {
            timer = signature.getMethod().getDeclaringClass().getDeclaredAnnotation(Timer.class);
        }
        String message = timer.value();
        if (!"".equals(message)) {
            log.info(message + "开始");
        }
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long during = System.currentTimeMillis() - start;
        if ("".equals(message)) {
            log.info(signature.getDeclaringType().getSimpleName() + "#" + signature.getName() + ": " + during + "ms");
        } else {
            log.info(message + "结束: " + during + "ms");
        }
        return result;
    }
}
