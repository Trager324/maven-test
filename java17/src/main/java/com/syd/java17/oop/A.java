package com.syd.java17.oop;

/**
 * @author SYD
 * @description
 * @date 2022/5/14
 */
public class A {

    public A getInstance(A a) {
        return a;
    }

    @Override
    protected A clone() throws CloneNotSupportedException {
        return (A)super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
