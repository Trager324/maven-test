package com.syd.java8;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;

import java.nio.charset.Charset;

/**
 * @author syd
 * @date 2022/3/20
 */
public class Solution {
    public static void main(String[] args) {
        System.out.println(Charset.defaultCharset());
    }

    @Data
    static class A<T> {
        T data;
    }
}
