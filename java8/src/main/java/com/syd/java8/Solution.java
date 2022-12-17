package com.syd.java8;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.Data;

/**
 * @author syd
 * @date 2022/3/20
 */
public class Solution {
    public static void main(String[] args) {
        TypeReference<A<A<Integer>>> type = new TypeReference<>() {
        };
        A<A<Integer>> data = JSON.parseObject("{\"data\":{\"data\":1}}", type);
        System.out.println(JSON.toJSONString(data));
    }

    @Data
    static class A<T> {
        T data;
    }
}
