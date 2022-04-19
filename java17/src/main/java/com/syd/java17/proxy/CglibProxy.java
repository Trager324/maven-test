package com.syd.java17.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import javax.tools.JavaCompiler;

/**
 * @author SYD
 * @description
 * @date 2021/9/17
 */
public class CglibProxy {
    static class A {
        void test() {
            System.out.println(1);
        }
    }
    public static void main(String[] args) {
        A a = new A();
//        JavassistSerialStateHolder
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(A.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("--- begin ---");
            Object res = method.invoke(a, objects);
            System.out.println("---- end ----");
            return res;
        });
        ((A) enhancer.create()).test();
        long l;
        int i;
        short s;
        char c;
        byte b;
        boolean g;
        float f;
        double d;
    }
}
