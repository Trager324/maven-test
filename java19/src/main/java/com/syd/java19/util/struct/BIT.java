package com.syd.java19.util.struct;

/**
 * 树状数组，binary indexed tree
 *
 * @author songyide
 * @date 2022/10/23
 */
public class BIT {
    long[] tree;
    int n;

    public BIT(int n) {
        this.n = n + 1;
        tree = new long[this.n];
    }

    static int lowBit(int x) {
//        return x & (~x + 1);
        return x & -x;
    }

    public void add(int i, long val) {
        i++;
        while (i < n) {
            tree[i] += val;
            i += lowBit(i);
        }
    }

    public long query(int i) {
        long res = 0;
        i++;
        while (i > 0) {
            res += tree[i];
//            i &= i - 1;
            i -= lowBit(i);
        }
        return res;
    }
}
