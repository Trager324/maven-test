package org.behappy.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 反射工具类. 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 *
 * @author ruoyi
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReflectUtils {

    /**
     * 获取按{@link Modifier}修饰符过滤的反射对象流
     *
     * @param memberArray 使用getDeclaredXxx getXxx等函数获取的反射对象数组
     * @param modifiers   反射修饰符
     * @return 过滤的反射对象流
     */
    public static <E extends AccessibleObject & Member> Stream<E> modifierFilteredStream(
            @NonNull E[] memberArray,
            int modifiers) {
        return Arrays.stream(memberArray)
                .filter(ao -> {
                    int mod = ao.getModifiers();
                    // 判断是否包含所有修饰符
                    return (mod & modifiers) == modifiers;
                });
    }

    /**
     * {@link com.alibaba.fastjson2.util.BeanUtils#getGenericSupertype(Type, Class, Class)}
     */
    @SuppressWarnings("all")
    public static Type getGenericSupertype(Type context, Class<?> rawType, Class<?> toResolve) {
        if (toResolve == rawType) {
            return context;
        }

        // we skip searching through interfaces if unknown is an interface
        if (toResolve.isInterface()) {
            Class<?>[] interfaces = rawType.getInterfaces();
            for (int i = 0, length = interfaces.length; i < length; i++) {
                if (interfaces[i] == toResolve) {
                    return rawType.getGenericInterfaces()[i];
                } else if (toResolve.isAssignableFrom(interfaces[i])) {
                    return getGenericSupertype(rawType.getGenericInterfaces()[i], interfaces[i], toResolve);
                }
            }
        }

        // check our supertypes
        if (!rawType.isInterface()) {
            while (rawType != Object.class) {
                Class<?> rawSupertype = rawType.getSuperclass();
                if (rawSupertype == toResolve) {
                    return rawType.getGenericSuperclass();
                } else if (toResolve.isAssignableFrom(rawSupertype)) {
                    return getGenericSupertype(rawType.getGenericSuperclass(), rawSupertype, toResolve);
                }
                rawType = rawSupertype;
            }
        }

        // we can't resolve this further
        return toResolve;
    }
}
