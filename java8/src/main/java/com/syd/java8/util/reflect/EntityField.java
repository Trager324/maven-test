package com.syd.java8.util.reflect;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
@TableName("trade_api_field")
public class EntityField {
    @TableId("id")
    private String id;
    @TableField("api_id")
    private String apiId;
    @TableField("field_type")
    private String fieldType;
    @TableField("field_name")
    private String fieldName;
    @TableField("description")
    private String description;
    @TableField("type")
    private String type;
    @TableField("p_id")
    private String pId;


    static <T extends Member> Predicate<T> filterModMatchNone(int mods) {
        return mem -> (mem.getModifiers() & mods) == 0;
    }

    static String getDescription(@NonNull AnnotatedElement ae) {
        return getDescription(ae, ApiModelProperty.class, ApiModelProperty::value);
    }

    static <A extends Annotation> String getDescription(@NonNull AnnotatedElement ae,
                                                        @NonNull Class<A> annoClass,
                                                        @NonNull Function<A, String> descGetter) {
        A anno = AnnotationUtils.getAnnotation(ae, annoClass);
        if (anno == null)
            return null;
        return descGetter.apply(anno);
    }

    static String getJavaStyleName(@NonNull Type type) {
        return getJavaStyleName(type, true);
    }

    static String getJavaStyleName(@NonNull Type type, boolean needPrefix) {
        if (type instanceof Class) {
            Class<?> clz = (Class<?>) type, ec;
            return (needPrefix && (ec = clz.getEnclosingClass()) != null ?
                    ec.getSimpleName() + "." : "") + clz.getSimpleName();
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type ownerType = pt.getOwnerType();
            String prefix = "";
            if (ownerType != null)
                prefix = getJavaStyleName(ownerType) + ".";
            return prefix + getJavaStyleName(pt.getRawType(), false) + "<" +
                    Stream.of(pt.getActualTypeArguments())
                            .map(EntityField::getJavaStyleName)
                            .collect(Collectors.joining(",")) +
                    ">";
        } else if (type instanceof GenericArrayType) {
            return getJavaStyleName(((GenericArrayType) type).getGenericComponentType()) + "[]";
        } else if (type instanceof WildcardType) {
            WildcardType wt = (WildcardType) type;
            if (wt.getLowerBounds().length != 0) {
                return "? super " + Stream.of(wt.getLowerBounds())
                        .map(EntityField::getJavaStyleName)
                        .collect(Collectors.joining("&"));
            } else if (wt.getUpperBounds().length != 0) {
                String upDesc = Stream.of(wt.getUpperBounds())
                        .map(EntityField::getJavaStyleName)
                        .collect(Collectors.joining("&"));
                return ("Object".equals(upDesc)) ? "?" : "? extend " + upDesc;
            }
            throw new AssertionError("Meaning less WildcardType");
        }
        return type.getTypeName();
    }

    public static List<EntityField> getEntityFields(Class<?> clz, String apiId) {
        return Stream.of(clz.getDeclaredFields())
                .filter(filterModMatchNone(Modifier.TRANSIENT | Modifier.STATIC))
                .map(f -> EntityField.builder()
                        .apiId(apiId)
                        .fieldName(f.getName())
                        .fieldType(getJavaStyleName(f.getGenericType()))
                        .description(getDescription(f))
                        .build()
                ).collect(Collectors.toList());
    }
}