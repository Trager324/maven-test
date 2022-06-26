package com.syd.java17.test.proxy;

import java.lang.reflect.Proxy;

/**
 * @author syd
 * @date 2021/9/17
 */
public class JdkProxy {
    interface IA {
        void test();
    }
    static class A implements IA {

        @Override
        public void test() {
            System.out.println(1);
        }
    }

    public static void main(String[] args){
        A a = new A();
        IA o = (IA) Proxy.newProxyInstance(
                a.getClass().getClassLoader(),
                a.getClass().getInterfaces(),
                (p, m, as) -> {
                    System.out.println("begin");
                    Object t = m.invoke(a, as);
                    System.out.println("end");
                    return t;
                });
        o.test();
        a.test(); //
        o.test(); //
    }
}
