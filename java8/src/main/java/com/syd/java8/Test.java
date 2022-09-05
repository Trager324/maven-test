package com.syd.java8;

/**
 * @author syd
 * @date 2021/12/3
 */
public class Test {
    public static void main(String[] args) {
        ((A)A -> A.a(A)).a(A -> A.a(A));
    }

    interface A {
        A a(A A);
    }
}
