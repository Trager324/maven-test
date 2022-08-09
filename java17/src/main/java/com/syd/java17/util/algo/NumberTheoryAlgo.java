package com.syd.java17.util.algo;

import java.util.ArrayList;
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
}
