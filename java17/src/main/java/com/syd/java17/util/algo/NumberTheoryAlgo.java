package com.syd.java17.util.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songyide
 * @date 2022/8/4
 */
public class NumberTheoryAlgo {
    /**
     * 分解质因数
     */
    public static List<Integer> breakdown(long n) {
        List<Integer> res = new ArrayList<>();
        for (int i = 2; (long)i * i <= n; i++) {
            if (n % i == 0) {
                // 如果 i 能够整除 N，说明 i 为 N 的一个质因子。
                while (n % i == 0) {
                    n /= i;
                }
                res.add(i);
            }
        }
        if (n != 1) {
            // 说明再经过操作之后 N 留下了一个素数
            res.add((int)n);
        }
        return res;
    }

    /**
     * 辗转相除法取最大公约数
     */
    public static int gcd(int a, int b) {
        while (b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    /**
     * 埃氏筛
     *
     * @param n 范围，闭合
     * @return 返回标记数组
     */
    public static boolean[] eratosthenes(int n) {
        boolean[] res = new boolean[n + 1];
        Arrays.fill(res, true);
        res[0] = res[1] = false;
        for (int i = 2; i * i <= n; i++) {
            if (res[i]) {
                for (int j = i * i; j <= n; j += i) {
                    res[j] = false;
                }
            }
        }
        return res;
    }

    /**
     * 线性素数筛
     *
     * @param n 范围，闭合
     * @return 返回标记数组
     */
    public static boolean[] primeSievesLeaner(int n) {
        boolean[] res = new boolean[n + 1];
        Arrays.fill(res, true);
        res[0] = res[1] = false;
        List<Integer> list = new ArrayList<>();
        for (int i = 2; i <= n; ++i) {
            if (res[i]) {
                list.add(i);
            }
            for (Integer prime : list) {
                if (i * prime > n) {
                    break;
                }
                res[i * prime] = false;
                if (i % prime == 0) {
                    // i % pri[j] == 0
                    // 换言之，i 之前被 pri[j] 筛过了
                    // 由于 pri 里面质数是从小到大的，所以 i 乘上其他的质数的结果一定也是
                    // pri[j] 的倍数 它们都被筛过了，就不需要再筛了，所以这里直接 break
                    // 掉就好了
                    break;
                }
            }
        }
        return res;
    }
}
