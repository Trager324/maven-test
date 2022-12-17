package com.syd.common.bean.bo.enumeration;

import com.syd.common.constant.ResponseCode;
import com.syd.common.exception.BaseException;
import com.syd.common.util.ExtCollectionUtils;
import com.syd.common.util.StreamUtils;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 通过指定code反序列化的枚举
 *
 * @author songyide
 * @date 2022/12/6
 */
public interface ICodeEnum {
    /**
     * 请勿直接使用
     */
    Map<Class<?>, Map<String, ?>> ENUM_CACHE = new ConcurrentHashMap<>();

    /**
     * 获取枚举实现的指标映射的默认方法
     *
     * @param clz 枚举clz
     * @param <T> 枚举类型
     * @return 默认的指标映射
     */
    @SuppressWarnings("all")
    static <T extends Enum<T> & ICodeEnum> Map<String, T> getIndexMap(Class<T> clz) {
        Map res;
        if ((res = ENUM_CACHE.get(clz)) == null) {
            synchronized (clz) {
                if ((res = ENUM_CACHE.get(clz)) == null) {
                    Map<String, ?> map = Arrays.stream(clz.getEnumConstants())
                            .collect(Collectors.toMap(
                                    // 此处使用方法引用会引起动态调用点异常，因为IAttribution作为第二个泛型参数无法匹配
                                    // 详见<https://stackoverflow.com/questions/33929304/weird-exception-invalid-receiver-type-class-java-lang-object-not-a-subtype-of>
                                    e -> e.getCode(),
                                    e -> e,
                                    StreamUtils.throwingMerger(),
                                    () -> ExtCollectionUtils.newCaseInsensitiveMap()));
                    ENUM_CACHE.put(clz, map);
                    res = map;
                }
            }
        }
        return res;
    }

    static <T extends Enum<T> & ICodeEnum> T ofNullable(Class<T> clz, String code) {
        if (code == null) {
            // TreeMap get null会引发NPE
            return null;
        }
        return getIndexMap(clz).get(code);
    }

    @SuppressWarnings("all")
    static <T extends Enum<T> & ICodeEnum & Describable> String getDescription(Class<T> clz, String code) {
        return Optional.ofNullable(ofNullable(clz, code))
                .map(p -> p.getDescription())
                .orElse(code);
    }

    static <T extends Enum<T> & ICodeEnum> T ofNunNull(Class<T> clz, String code) {
        var res = ofNullable(clz, code);
        if (res == null) {
            throw BaseException.of(ResponseCode.A0400, "不合法的key - " + code)
                    .setDebugInfo(clz.getName());
        }
        return res;
    }

    /**
     * 获取枚举键
     *
     * @return 枚举键
     */
    @NonNull
    String getCode();
}
