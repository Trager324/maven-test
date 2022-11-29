package com.syd.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 字符串工具类
 *
 * @author songyide
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtStrUtils extends StringUtils {

    public static final String COMMA = ",";
    public static final String PERIOD = ".";

    /**
     * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param c 要判断的Collection
     * @return 判断结果
     */
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

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

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str) {
        return String.join("_", splitByCharacterTypeCamelCase(str)).toLowerCase();
    }

    /**
     * 下划线转驼峰式命名法
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        int size = 0;
        char[] cs = s.toCharArray();
        boolean upperCase = false;
        for (char c : cs) {
            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                cs[size++] = Character.toUpperCase(c);
                upperCase = false;
            } else {
                cs[size++] = Character.toLowerCase(c);
            }
        }
        return new String(cs, 0, size);
    }

    public static String leftPad(int num, int size) {
        return leftPad(String.valueOf(num), size, '0');
    }

    public static String sliceWhenOverLength(Object o, int length) {
        if (o == null) {
            return null;
        }
        String s = o.toString();
        return s.length() > length ? s.substring(0, length) : s;
    }

    /**
     * 获取参数不为空值
     * <p><b>注意如果参数是表达式会先被计算，如果表达式具有一定计算量请使用条件分支语句！</b></p>
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T ifNull(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}