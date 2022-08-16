package com.syd.java17.util.algo;

import java.util.Comparator;
import java.util.List;

/**
 * @author syd
 * @date 2022/3/10
 */
public class MathAlgo {
    public static final int MOD = 1000000007;

    public static long combine(int m, int n) {
        long res = 1;
        n = Math.min(n, m - n);
        for (int i = 1; i <= n; i++) {
            res = res * (m - i + 1) / i;
        }
        return res;
    }

    public static long binPow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = res * a;
            }
            a = a * a;
            b >>= 1;
        }
        return res;
    }

    private MathAlgo() {}
}
