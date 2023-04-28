package com.syd.java21.struct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Stream;

class EntityFieldTest<T extends Enum<T>> {
    @Test
    void getEntityFields() {
        EntityField.getEntityFields(ClassA.class, "api_id").forEach(System.out::println);
        System.out.println();
        EntityField.getEntityFields(ClassB.class, "api_id").forEach(System.out::println);

        class ClassC {
            @ApiModelProperty("标识")
            String key;
            @ApiModelProperty("标签")
            String label;
            @ApiModelProperty("子节点")
            List<ClassC> children;
        }
        System.out.println();
        EntityField.getEntityFields(ClassC.class, "api_id").forEach(System.out::println);

        System.out.println();
        EntityField.getEntityFields(new Object() {
            @ApiModelProperty("标识")
            String key;
            @ApiModelProperty("标签")
            String label;
            @ApiModelProperty("子节点")
            List<Object> children;
        }.getClass(), "api_id").forEach(System.out::println);
        System.out.println();
        EntityField.getEntityFields(ClassD.class, "api_id").forEach(System.out::println);
    }

    @ApiModel
    @Data
    static class ClassB {
        @ApiModelProperty("标识")
        String key;
        @ApiModelProperty("标签")
        String label;
        @ApiModelProperty("子节点")
        List<ClassB> children;
    }

    @ApiModel
    @Data
    class ClassA<U extends BiFunction<?, ?, ?>> {
        @ApiModelProperty("整数")
        int v_int;
        @ApiModelProperty("字符串")
        String v_str;
        @ApiModelProperty("数组")
        char[] v_char_array;
        @ApiModelProperty("多级数组")
        float[][][] v_multilevel_arr;
        @ApiModelProperty("泛型类型")
        EnumSet<T> v_generic;
        @ApiModelProperty("通配列表")
        List<? super U[][]> v_list;
        @ApiModelProperty("泛型数组")
        Consumer<? super T>[] v_generic_array;
        @ApiModelProperty("嵌套泛型数组")
        Stream<? extends Stream<? extends Object>>[] v_nested_generic_array;
        @ApiModelProperty("复杂混合类型")
        @SuppressWarnings("rawtypes")
        Map<? extends T, ClassA<? super BinaryOperator<Map.Entry<Function<U[], Supplier>, Class<?>>>>[]> v_complicated_mixed;
    }
}

class ClassD {
    @ApiModelProperty("标识")
    String key;
    @ApiModelProperty("标签")
    String label;
    @ApiModelProperty("子节点")
    List<ClassD> children;
}
