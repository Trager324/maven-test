package com.syd.java17.test.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author syd
 * @date 2021/9/17
 */
public class CglibProxy {
    public static void main(String[] args) {
        A a = new A();
//        JavassistSerialStateHolder
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(A.class);
        enhancer.setCallback((MethodInterceptor)(o, method, objects, methodProxy) -> {
            System.out.println("--- begin ---");
            Object res = method.invoke(a, objects);
            System.out.println("---- end ----");
            return res;
        });
        ((A)enhancer.create()).test();
    }

    static class A {
        void test() {
            System.out.println(1);
        }
    }
}
