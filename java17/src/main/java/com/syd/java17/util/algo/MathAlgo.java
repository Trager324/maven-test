package com.syd.java17.util.algo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
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
     * 快速幂法求乘法逆元，对{@link #MOD}取余
     * <p>根据费马小定理，条件为b为素数</p>
     * <p>条件弱化为{@code gcd(a, b) == 1}需要使用扩展欧几里得算法</p>
     *
     * @return a mod b 的逆元
     */
    public static int multiplicativeInverse(long a, int b) {
        int res = 1;
        a = (a % MOD + MOD) % MOD;
        for (; b != 0; b >>= 1) {
            if ((b & 1) == 1) {
                res = (int)(a * res % MOD);
            }
            a = a * a % MOD;
        }
        return res;
    }

    /**
     * 线性求乘法逆元，对{@link #MOD}取余
     *
     * @param n 范围
     * @return 逆元数组
     */
    public static long[] multiplicativeInverseLeaner(int n) {
        long[] inv = new long[n + 1];
        inv[1] = 1;
        for (int i = 2; i <= n; i++) {
            inv[i] = (MOD - MOD / i) * inv[MOD % i] % MOD;
        }
        return inv;
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
        return (int)res;
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
