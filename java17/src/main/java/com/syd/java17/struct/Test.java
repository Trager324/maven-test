package com.syd.java17.struct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Assert;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.function.Function;

/**
 * @author SYD
 * @description
 * @date 2022/1/25
 */
strictfp public class Test {
    static char[] cs1 = "qeijfwnb9u13b89#HBY@BU!09B@!(B|8\n926@！*）hGW".toCharArray();
    static boolean[] map = new boolean[65536];
    static {
        for (int i = 'a', j = 'A'; i <= 'z'; i++, j++) {
            map[i] = map[j] = true;
        }
    }
    static char[] getCs1(int length) {
        char[] cs = new char[length];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            cs[i] = (char)random.nextInt(128);
        }
        return cs;
    }
    public static <T> T getTimerProxy(T t) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(t.getClass());
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            long begin = System.nanoTime();
            Object res = method.invoke(t, objects);
            long during = System.nanoTime() - begin, second = during / 1000000000;
            System.out.printf("%s用时%d.%09d秒\n", method.getName(), second, during - second * 1000000000);
            return res;
        });
        @SuppressWarnings("unchecked")
        T  res = (T) enhancer.create();
        return res;
    }
    static void assertIsLetter(char c, boolean result, boolean excepted) {
        if (excepted != result) {
            Assert.fail("%c excepted %b, but was %b".formatted(c, excepted, result));
        }
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
    static void test(char[] cs) {
        Test test = getTimerProxy(new Test());
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
    int[][] avgNums;
    int[] resNums;
    int[] expectedNums;
    long start, stop;
    Random random = ThreadLocalRandom.current();
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

    @org.junit.Test
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
        Assert.assertArrayEquals(expectedNums, resNums);

    }

    class QueuedTagReader {
        static final int CAPACITY = 1024;
        static final int S1 = 1, S2 = 2;
        static final byte CHAR_START = (byte)'{', CHAR_END = (byte)'}';

        byte[] queue = new byte[CAPACITY];
        // 队头
        private int front = 0;
        // 队尾
        private int rear = 0;
        // 扫描索引
        private int idx = 0;
        // 当前状态
        private int state = S1;

        InputStream in;

        QueuedTagReader(InputStream in) {
            this.in = in;
        }

        JSONObject readTag() {
            JSONObject tag = null;
            try {
                while (tag == null) {
                    int c = in.available();
                    if (c == 0) {
                        Thread.sleep(100);
                        continue;
                    }
                    rear += in.read(queue, rear, c);
                    while (idx < rear) {
                        byte b = queue[idx++];
                        if (state == S1) {
                            if (b == CHAR_START) {
                                state = S2;
                                front = idx;
                            }
                        } else {
                            if (b == CHAR_END) {
                                // TODO 获取tag
                                tag = new JSONObject();
                                idx = front = rear = 0;
                                state = S1;
                                break;
                            } else if (b == CHAR_START) {
                                front = idx;
                            }
                        }
                    }
                }
            } catch (IOException | InterruptedException | ArrayIndexOutOfBoundsException e) {
                state = S1;
                e.printStackTrace();
            }
            return tag;
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = new ThreadPoolExecutor(
                1,1,0,TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.AbortPolicy());
        executor.execute(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Map<Integer, Integer> map = new HashMap<>();
    }

}
