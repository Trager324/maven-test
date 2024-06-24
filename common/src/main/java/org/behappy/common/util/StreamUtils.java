package org.behappy.common.util;

import com.alibaba.fastjson2.JSONArray;
import org.behappy.common.constant.ResponseCode;
import org.behappy.common.exception.BaseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * 流式计算工具类
 *
 * @author songyide
 * @date 2022/10/20
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamUtils {
    public static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw BaseException.of(ResponseCode.B0001)
                    .appendDebugInfo("field字段重复\nu: " + u + "\nv: " + v);
        };
    }

    /**
     * 参数如果不为空则执行映射函数
     */
    public static <T, R> R nonNullThen(T t, Function<T, R> func) {
        if (t == null)
            return null;
        return func.apply(t);
    }

    /**
     * 为JSONArray等列表提供的取值函数
     *
     * @param func 可使用方法引用如{@link JSONArray#getJSONObject}以取得预期的类型
     */
    public static <T extends List<?>, R> R accessibleThen(T a, int idx, BiFunction<T, Integer, R> func) {
        if (a == null || idx >= a.size() || idx < 0)
            return null;
        return func.apply(a, idx);
    }

    public static <K, V> Map.Entry<K, V> newEntry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
