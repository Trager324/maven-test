package com.syd.java17.oop;

/**
 * @author SYD
 * @description
 * @date 2022/5/14
 */
public class B extends A {

    @Override
    public A getInstance(A a) {
        return super.getInstance((A)a);
    }
}
