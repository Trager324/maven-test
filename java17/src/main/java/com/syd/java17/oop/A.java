package com.syd.java17.oop;

/**
 * @author syd
 * @date 2022/5/14
 */
public class A {
    private int a;

    interface IA {
        static void test() {
            System.out.println("A.IA.test");
        }
        private void a() {
            System.out.println("A.IA.a");
        }
    }

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
