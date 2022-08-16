package com.syd.java17.util;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.RepeatedTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author songyide
 * @date 2022/6/17
 */
public class FastJsonTest {
    @Data
    static class Response<T> {
        T data;

        static <T> Response<T> success(T data) {
            Response<T> response = new Response<>();
            response.data = data;
            return response;
        }
    }

    @RepeatedTest(100)
    void testLandUseProfile() {
        System.out.println(JSON.toJSONString(rLandUse()));
    }

    @RepeatedTest(100)
    void testIndustrialProfile() {
        System.out.println(JSON.toJSONString(rlB()));
    }

    @RepeatedTest(100)
    void testRealEstateProfile() {
        System.out.println(JSON.toJSONString(rlB()));
    }

    public Response<B> rLandUse() {
        return Response.success(new B());
    }

    @Data
    static class A {
        int a;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    static class B extends A {
        int b;
    }

    public Response<List<Integer>> rlB() {
        return Response.success(Collections.singletonList(1));
    }
}
