package com.syd.java8;

import org.junit.jupiter.api.Test;

public class JDKTest {
    @Test
    void test() {
        java.util.BitSet bitSet = new java.util.BitSet(Integer.MAX_VALUE);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            bitSet.set(i);
        }
        System.out.println(bitSet.size());
    }
}

class Hello {
    public void test() {
        int i = 8;
        while ((i -= 3) > 0) ;
        System.out.println("i = " + i);
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        for (int i = 0; i < 50_000; i++) {
            hello.test();
        }
    }
}
