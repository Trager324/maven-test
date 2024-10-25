package org.behappy.java.algo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static org.behappy.java.algo.NumericAlgo.multiplicativeInverseLeaner;

/**
 * 其他数学算法，包含：组合数学、快速幂
 *
 * @author syd
 * @date 2022/3/10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MathAlgo {
    public static final int MOD = 1_000_000_007;

    /**
     * 快速幂，对{@link #MOD}取余
     *
     * @param a 底数
     * @param b 指数
     * @return a ** b
     */
    public static long binPow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            b >>= 1;
        }
        return res;
    }

    /**
     * 组合数，对{@link #MOD}取余
     *
     * @param n 总数
     * @param m 取m个数
     * @return C(n, m)
     */
    public static int combine(int n, int m) {
        long[] inv = multiplicativeInverseLeaner(n);
        // 递推求组合数，初值 C(n, 0) = 1
        long res = 1;
        for (int i = 1; i <= m; i++) {
            res = res * (n - i + 1) % MOD * inv[i] % MOD;
        }
        return (int) res;
    }

    /**
     * 用大数计算的组合数
     *
     * @param n 总数
     * @param m 取m个数
     * @return C(n, m)
     */
    public static BigInteger combineBigInteger(int n, int m) {
        BigInteger res = BigInteger.ONE;
        m = Math.min(m, n - m);
        for (int i = 1; i <= m; i++) {
            res = res.multiply(BigInteger.valueOf(n - i + 1)).divide(BigInteger.valueOf(i));
        }
        return res;
    }
}
