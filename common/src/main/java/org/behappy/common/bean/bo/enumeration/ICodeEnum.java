package org.behappy.common.bean.bo.enumeration;

import org.behappy.common.constant.ResponseCode;
import org.behappy.common.exception.BaseException;
import org.behappy.common.util.MapUtils;
import org.behappy.common.util.StreamUtils;
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
    static <T extends Enum<T> & ICodeEnum> Map<String, T> getIndexMap(Class<T> clz) {
        @SuppressWarnings("rawtypes")
        Map map = ENUM_CACHE.computeIfAbsent(clz, k -> Arrays.stream(clz.getEnumConstants())
                .collect(Collectors.toMap(
                        // 此处使用方法引用会引起动态调用点异常，因为IAttribution作为第二个泛型参数无法匹配
                        // 详见<https://stackoverflow.com/questions/33929304/weird-exception-invalid-receiver-type-class-java-lang-object-not-a-subtype-of>
                        ICodeEnum::getCode,
                        e -> e,
                        StreamUtils.throwingMerger(),
                        MapUtils::<T>newCaseInsensitiveMap)));
        @SuppressWarnings("unchecked")
        var res = (Map<String, T>)map;
        return res;
    }

    static <T extends Enum<T> & ICodeEnum> T ofNullable(Class<T> clz, String code) {
        if (code == null) {
            // TreeMap get null会引发NPE
            return null;
        }
        return getIndexMap(clz).get(code);
    }

    static <T extends Enum<T> & ICodeEnum & Describable> String getDescription(Class<T> clz, String code) {
        @SuppressWarnings("Convert2MethodRef")
        var res = Optional.ofNullable(ofNullable(clz, code))
                .map(p -> p.getDescription())
                .orElse(code);
        return res;
    }

    static <T extends Enum<T> & ICodeEnum> T ofNunNull(Class<T> clz, String code) {
        var res = ofNullable(clz, code);
        if (res == null) {
            throw BaseException.of(ResponseCode.A0400, "不合法的key - " + code)
                    .appendDebugInfo(clz.getName());
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
