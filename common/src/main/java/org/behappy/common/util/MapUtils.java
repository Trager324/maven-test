package org.behappy.common.util;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Map;

/**
 * Map工具类，更多方法使用{@link Maps}
 *
 * @author songyide
 * @see Maps
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MapUtils {
    /**
     * 不区分大小写的Map, 允许null值
     */
    public static <T> Map<String, T> newCaseInsensitiveMap() {
        return new LinkedCaseInsensitiveMap<>();
    }

    /**
     * 不区分大小写的Map, 允许null值
     */
    public static <T> Map<String, T> newCaseInsensitiveMap(Map<String, T> m) {
        var map = new LinkedCaseInsensitiveMap<T>();
        map.putAll(m);
        return map;
    }
}
