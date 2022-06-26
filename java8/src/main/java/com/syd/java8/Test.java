package com.syd.java8;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author syd
 * @date 2021/12/3
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Constructor<?> ctor = Enum.class.getDeclaredConstructor(String.class, int.class);
        ctor.setAccessible(true);
        System.out.println(Arrays.toString(ctor.newInstance("", 0).getClass().getDeclaredMethods()));
        System.out.println(1.1 % 0.5);
        Lock lock;
        Condition condition;
        new ConcurrentHashMap<>();
    }


    public static void method(String param) {
        switch (param) {
            // 肯定不是进入这里
            case "sth":
                System.out.println("it's sth");
                break;
            // 也不是进入这里
            case "null":
                System.out.println("it's null");
                break;
            // 也不是进入这里
            default:
                System.out.println("default");
        }
    }

}
