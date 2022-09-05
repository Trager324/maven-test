package com.syd.java17.struct;

//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;
//import org.junit.Assert;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author syd
 * @date 2022/1/25
 */
strictfp public class MyTest {
    static char[] cs1 = "qeijfwnb9u13b89#HBY@BU!09B@!(B|8\n926@！*）hGW".toCharArray();
    static boolean[] map = new boolean[65536];

    static {
        for (int i = 'a', j = 'A'; i <= 'z'; i++, j++) {
            map[i] = map[j] = true;
        }
    }

    int[][] avgNums;
    int[] resNums;
    int[] expectedNums;
    long start, stop;
    Random random = ThreadLocalRandom.current();

    static char[] getCs1(int length) {
        char[] cs = new char[length];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            cs[i] = (char)random.nextInt(128);
        }
        return cs;
    }

    public static <T> T getTimerProxy(T t) {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(t.getClass());
//        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
//            long begin = System.nanoTime();
//            Object res = method.invoke(t, objects);
//            long during = System.nanoTime() - begin, second = during / 1000000000;
//            System.out.printf("%s用时%d.%09d秒\n", method.getName(), second, during - second * 1000000000);
//            return res;
//        });
//        @SuppressWarnings("unchecked")
//        T res = (T) enhancer.create();
//        return res;
        return t;
    }

    static void assertIsLetter(char c, boolean result, boolean excepted) {
        if (excepted != result) {
//            Assert.fail("%c excepted %b, but was %b".formatted(c, excepted, result));
        }
    }

    static void test(char[] cs) {
        MyTest test = getTimerProxy(new MyTest());
        int length = cs.length;
        boolean[] answer = new boolean[length];
        for (int i = 0; i < length; i++) {
            char c = cs[i];
            answer[i] = c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
        }
        System.out.printf("长度%d\n", length);
        test.check1(cs, answer, length);
        test.check2(cs, answer, length);
        test.check3(cs, answer, length);
    }

    public static void main(String[] args) throws Exception {
        new MyTest().testAvg1();
    }

    void check1(char[] cs, boolean[] answer, int length) {
        for (int i = 0; i < length; i++) {
            char c = cs[i];
            assertIsLetter(c, Character.isLetter(c), answer[i]);
        }
    }

    void check2(char[] cs, boolean[] answer, int length) {
        for (int i = 0; i < length; i++) {
            char c = cs[i];
            boolean flag = c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
            assertIsLetter(c, flag, answer[i]);
        }
    }

    void check3(char[] cs, boolean[] answer, int length) {
        for (int i = 0; i < length; i++) {
            char c = cs[i];
            assertIsLetter(c, map[c], answer[i]);
        }
    }

    public void initNumber(int n) {
        avgNums = new int[n][2];
        expectedNums = new int[n];
        resNums = new int[n];
        for (int i = 0; i < n; i++) {
            avgNums[i][0] = random.nextInt(Integer.MAX_VALUE);
            avgNums[i][1] = random.nextInt(Integer.MAX_VALUE);
        }
    }

    public void timerStart() {
        start = System.nanoTime();
    }

    public void timerStop() {
        long during = System.nanoTime() - start, second = during / 1000000000;
        System.out.printf("用时%d.%09d秒\n", second, during - second * 1000000000);
    }

    public void testAvg1() {
        int n = 10000000;
        initNumber(n);
        timerStart();
        for (int i = 0; i < n; i++) {
            int a = avgNums[i][0], b = avgNums[i][1];
            expectedNums[i] = a + ((b - a) >> 1);
        }
        timerStop();
        timerStart();
        for (int i = 0; i < n; i++) {
            int a = avgNums[i][0], b = avgNums[i][1];
            resNums[i] = (a & b) + ((a ^ b) >> 1);
        }
        timerStop();
//        Assert.assertArrayEquals(expectedNums, resNums);

    }
}
