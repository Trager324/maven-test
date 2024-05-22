package com.syd.java.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.syd.java.algo.MathAlgo.MOD;

/**
 * 数论算法
 *
 * @author songyide
 * @date 2022/8/4
 */
public class NumericAlgo {
    /**
     * 分解质因数
     *
     * @param n 要分解的数
     * @return List[质因数, 指数]
     */
    public static List<long[]> breakdown(long n) {
        List<long[]> res = new ArrayList<>();
        for (int i = 2; (long) i * i <= n; i++) {
            int cnt = 0;
            while (n % i == 0) {
                n /= i;
                cnt++;
            }
            if (cnt > 0) res.add(new long[]{i, cnt});
        }
        if (n != 1) {
            // 说明再经过操作之后 N 留下了一个素数
            res.add(new long[]{n, 1});
        }
        return res;
    }

    /**
     * 最大公约数(辗转相除法/欧几里得算法)
     * <p>迭代版:</p>
     * {@snippet lang = java:
     * public static long gcd(long a, long b) {
     *     while (b != 0) {
     *         long r = a % b;
     *         a = b;
     *         b = r;
     *     }
     *     return a;
     * }
     *}
     *
     * @param a 第一个数
     * @param b 第二个数
     * @return a和b的最大公约数
     */
    public static long gcd(long a, long b) {return b == 0 ? a : gcd(b, a % b);}

    /**
     * 最小公倍数
     *
     * @param a 第一个数
     * @param b 第二个数
     * @return a和b的最小公倍数
     */
    public static long lcm(long a, long b) {return a * b / gcd(a, b);}


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
        // 根据素数定理，素数的个数大约为 n / ln(n)
        List<Integer> list = new ArrayList<>((int) (n / Math.log(n)));
        for (int i = 2; i <= n; ++i) {
            if (res[i]) {
                list.add(i);
            }
            for (var prime : list) {
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

    /**
     * 快速幂法求乘法逆元，对{@link MathAlgo#MOD}取余
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
                res = (int) (a * res % MOD);
            }
            a = a * a % MOD;
        }
        return res;
    }

    /**
     * 线性求乘法逆元，对{@link MathAlgo#MOD}取余
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
}
