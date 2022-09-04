package com.syd.java8;

import lombok.Data;

import java.util.Arrays;

/**
 * @author syd
 * @date 2021/12/3
 */
public class Test {
    public static void main(String[] args) {
        String s = "a2 g4v";
        String[] split = s.split("");
        System.out.println(Arrays.toString(split));
    }

    @Data
    public static class A {
        private String a;
    }

}
