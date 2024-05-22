package com.syd.java.algo.struct;

/**
 * @author songyide
 * @date 2022/8/4
 */
public class LongSegTree {
    // Let UNIQUE be a value which does NOT
    // and will not appear in the segment tree
    private static final long UNIQUE = 0;
    private final int size;
    // Segment tree values
    private final long[] tree;

    public LongSegTree(int size) {
        tree = new long[2 * (this.size = size)];
        java.util.Arrays.fill(tree, UNIQUE);
    }

    public LongSegTree(long[] values) {
        this(values.length);
        for (int i = 0; i < size; i++) modify(i, values[i]);
    }

    // This is the segment tree function we are using for queries.
    // The function must be an associative function, meaning
    // the following property must hold: f(f(a,b),c) = f(a,f(b,c)).
    // Common associative functions used with segment trees
    // include: min, max, sum, product, GCD, and etc...
    private long function(long a, long b) {
        //        return a + b; // sum over a range
        return Math.max(a, b); // maximum value over a range
        //        return Math.min(a, b); // minimum value over a range
        //        return a * b; // product over a range (watch out for overflow!)
    }

    // Adjust point i by a value, O(log(n))
    public void modify(int i, long value) {
        //tree[i + N] = function(tree[i + N], value);
        tree[i + size] = value;
        for (i += size; i > 1; i >>= 1) {
            tree[i >> 1] = function(tree[i], tree[i ^ 1]);
        }
    }

    // Query interval [l, r), O(log(n)) ----> notice the exclusion of r
    public long query(int l, int r) {
        long res = UNIQUE;
        for (l += size, r += size; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) != 0) res = function(res, tree[l++]);
            if ((r & 1) != 0) res = function(res, tree[--r]);
        }
        return res;
    }
}
