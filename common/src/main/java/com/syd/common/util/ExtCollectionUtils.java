package com.syd.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author songyide
 * @date 2022/12/6
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ExtCollectionUtils extends CollectionUtils {
    /**
     * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     * @return 判断结果
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return 判断结果
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
