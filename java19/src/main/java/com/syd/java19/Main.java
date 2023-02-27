package com.syd.java19;

import lombok.Data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author syd
 */
public class Main {
    @Data
    static class A<T> {
        String zyid;
        String zybh;
        T isHidden;
    }

    public static void main(String[] args) throws Exception {
        Object o = 1.0;
        System.out.println(o instanceof Double);
    }
}
