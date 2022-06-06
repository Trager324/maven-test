package com.syd.java17.util.algo;

import java.util.List;

/**
 * @author SYD
 * @description
 * @date 2022/3/10
 */
public class MathUtils {
    static final boolean[] NOT_PRIME_MAP = new boolean[101];

    static {
        for (int i = 2; i < 101; i++) {
            if (!NOT_PRIME_MAP[i]) {
                for (int j = i; j * i < 101; j++) {
                    NOT_PRIME_MAP[i * j] = true;
                }
            }
        }
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    /**
     * 二分搜索，升序列表，不大于x
     */
    public static <T> int bsFlooringEqual(List<? extends Comparable<T>> list, T x) {
        int l = -1, r = list.size();
        while (l != r - 1) {
            int m = l + ((r - l) >> 1);
            if (list.get(m).compareTo(x) <= 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    /**
     * 二分搜索，升序列表，严格小于x
     */
    public static <T> int bsFlooring(List<? extends Comparable<T>> list, T x) {
        return bsCeilingEqual(list, x) - 1;
    }

    /**
     * 二分搜索，升序列表，不小于x
     */
    public static <T> int bsCeilingEqual(List<? extends Comparable<T>> list, T x) {
        int l = -1, r = list.size();
        while (l != r - 1) {
            int m = l + ((r - l) >> 1);
            if (list.get(m).compareTo(x) < 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }

    /**
     * 二分搜索，升序列表，严格小于x
     */
    public static <T> int bsCeiling(List<? extends Comparable<T>> list, T x) {
        return bsFlooringEqual(list, x) + 1;
    }

    static int[] getNextArray(char[] cs) {
        int n = cs.length;
        int[] next = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && cs[i] != cs[j]) {
                j = next[j - 1];
            }
            if (cs[i] == cs[j]) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public static int kmpMatch(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int ns = cs.length, nt = ct.length;
        if (ns < nt) return -1;
        int[] next = getNextArray(ct);
        for (int i = 0, j = 0; i < ns; i++) {
            while (j > 0 && cs[i] != ct[j]) {
                j = next[j - 1];
            }
            if (cs[i] == ct[j]) {
                j++;
            }
            if (j == nt) {
                return i - j + 1;
            }
        }
        return -1;
    }

    private MathUtils() {}

    public static void main(String[] args) {
//        System.out.println(kmpMatch("BBC ABCDAB ABCDABCDABDE", "ABCDABD"));
//        System.out.println(kmpMatch("ABCDABC", "ABC"));
        System.out.println(kmpMatch("ABBAABBBABABBAABBABAAA", "BABBA"));
    }
}
