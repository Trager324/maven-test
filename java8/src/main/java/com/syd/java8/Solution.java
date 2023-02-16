package com.syd.java8;

import lombok.Data;

/**
 * @author syd
 * @date 2022/3/20
 */
public class Solution {
    public static void main(String[] args) {
        System.gc();
    }

    @Data
    static class A<T> {
        T data;
    }
}
