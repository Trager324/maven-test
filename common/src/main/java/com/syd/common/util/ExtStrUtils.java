package com.syd.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author songyide
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtStrUtils extends StringUtils {

    public static final String COMMA = ",";
    public static final String PERIOD = ".";
    public static final String STAR = "*";
    public static final String UNDERSCORE = "_";
    public static final String DOLLAR = "$";

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