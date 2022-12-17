package com.syd.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author ruoyi
 */
@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpringUtils implements ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext ac;

    /**
     * 获取实例
     *
     * @param name bean名称
     * @return 容器中的实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T)ac.getBean(name);
    }

    /**
     * 获取实例
     *
     * @param clz 类对象
     * @return 容器中的实例
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return ac.getBean(clz);
    }

    /**
     * 获取多实例
     *
     * @param type 类对象
     * @return 容器中的实例
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return ac.getBeansOfType(type);
    }


    /**
     * 获取注解实例
     *
     * @param annotationType 类对象
     * @return 容器中的实例
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return ac.getBeansWithAnnotation(annotationType);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean名称
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return ac.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常(NoSuchBeanDefinitionException)
     *
     * @param name bean名称
     * @return boolean
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return ac.isSingleton(name);
    }

    /**
     * @param name bean名称
     * @return 类型
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return ac.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name bean名称
     * @return 别名
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return ac.getAliases(name);
    }

    /**
     * 获取aop代理对象
     *
     * @param invoker 实例
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T)AopContext.currentProxy();
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles() {
        return ac.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return activeProfiles.length != 0 ? activeProfiles[0] : null;
    }

    @Override
    public synchronized void setApplicationContext(@NonNull ApplicationContext ac) throws BeansException {
        SpringUtils.ac = ac;
    }
}
