package com.syd.java17.oop;

/**
 * @author SYD
 * @date 2022/5/14
 */
public class A {
    private int a;

    public A getInstance(A a) {
        return a;
    }

    @Override
    protected A clone() throws CloneNotSupportedException {
        return (A)super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof A a) {
            return a.a == this.a;
        }
        return false;
    }
}
