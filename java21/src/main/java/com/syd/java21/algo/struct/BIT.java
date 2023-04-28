package com.syd.java21.algo.struct;

/**
 * 树状数组，binary indexed tree
 *
 * @author songyide
 * @date 2022/10/23
 */
public class BIT {
    final long[] c;
    final int n;
    final int bit;

    public BIT(int n) {
        this.n = n;
        c = new long[n + 1];
        for (int i = 0; ; i++) {
            if (1 << i >= n) {
                bit = i;
                break;
            }
        }
    }

    static int lowBit(int x) {
        // 等效于 -> return x & (~x + 1);
        return x & -x;
    }

    void checkIndex(int x) {
        if (x <= 0 || x > n) {
            throw new IllegalArgumentException("Illegal index " + x);
        }
    }

    public void modify(int x, long val) {
        checkIndex(x);
        for (; x <= n; x += lowBit(x)) {
            c[x] += val;
        }
    }

    public long query(int x) {
        checkIndex(x);
        long res = 0;
        // 等效于 -> x &= x - 1;
        for (; x > 0; x ^= lowBit(x)) {
            res += c[x];
        }
        return res;
    }

    /**
     * 树上二分，利用贪心思想将 O(log<sup>2</sup>N) 复杂度降到 O(logN)
     *
     * @param s 查询值
     * @return 位置
     */
    public int biQuery(long s) {
        int res = 0;
        for (int i = 1 << bit; i >= 1; i >>= 1) {
            if (res + i <= n && s >= c[res + i]) {
                res |= i;
                s -= c[i];
            }
        }
        return res;
    }
}
