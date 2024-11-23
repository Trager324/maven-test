package org.behappy.java;


import java.util.Map;

public class Main {


    static void f1(Map<String, ? extends Map<String, Integer>> map) {

    }

    public static boolean isUnsignedLongPowerOf2(long n) {
        return n != 0 && (n & (n - 1)) == 0;
    }

    public static void main(String[] args) {
        System.out.println(isUnsignedLongPowerOf2(128L));
        System.out.println(isUnsignedLongPowerOf2(Long.MAX_VALUE));
        System.out.println(isUnsignedLongPowerOf2(Long.MIN_VALUE));
    }
}
