package com.syd.java17.test;


import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString("   12fn31j*&@#%!@)(&^*#!421 \tof 3\n1 f31   13rn 31f".split("")));
        System.out.println(Arrays.toString("   12fn31j*&@#%!@)(&^*#!421 \tof 3\n1 f31   13rn 31f".split(".*?")));
    }
}
