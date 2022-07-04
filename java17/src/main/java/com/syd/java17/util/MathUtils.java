package com.syd.java17.util;

import java.util.Comparator;
import java.util.List;

/**
 * @author syd
 * @date 2022/3/10
 */
public class MathUtils {
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
     * 二分搜索，升序列表，严格小于x
     */
    public static <T> int bsCeiling(List<? extends T> list, T x, Comparator<? super T> cmp) {
        return bsFlooringEqual(list, x, cmp) + 1;
    }

    /**
     * 二分搜索，升序列表，严格小于x
     */
    public static <T> int bsFlooring(List<? extends T> list, T x, Comparator<? super T> cmp) {
        return bsCeilingEqual(list, x, cmp) - 1;
    }

    /**
     * 二分搜索，升序列表，不大于x
     */
    public static <T> int bsFlooringEqual(List<? extends T> list, T x, Comparator<? super T> cmp) {
        int l = -1, r = list.size();
        while (l != r - 1) {
            int m = l + ((r - l) >> 1);
            if (cmp.compare(list.get(m), x) <= 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    /**
     * 二分搜索，升序列表，不大于x
     */
    public static <T> int bsCeilingEqual(List<? extends T> list, T x, Comparator<? super T> cmp) {
        int l = -1, r = list.size();
        while (l != r - 1) {
            int m = l + ((r - l) >> 1);
            if (cmp.compare(list.get(m), x) < 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
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

    /**
     * 下一个排列
     */
    public static String nextPermutation(String s) {
        char[] cs = s.toCharArray();
        int n = cs.length - 1, i = n, j = n;
        while (i > 0 && cs[i] <= cs[i - 1]) {
            i--;
        }
        if (i == 0) {
            reverse(cs, 0, n);
            return new String(cs);
        }
        while (j >= 0 && cs[j] <= cs[i - 1]) {
            j--;
        }
        swap(cs, j, i - 1);
        reverse(cs, i, n);
        return new String(cs);
    }

    /**
     * 上一个排列
     */
    public static String prevPermutation(String s) {
        char[] cs = s.toCharArray();
        int n = cs.length - 1, i = n, j = n;
        while (i > 0 && cs[i] >= cs[i - 1]) {
            i--;
        }
        if (i == 0) {
            reverse(cs, 0, n);
            return new String(cs);
        }
        while (j >= 0 && cs[j] >= cs[i - 1]) {
            j--;
        }
        swap(cs, j, i - 1);
        reverse(cs, i, n);
        return new String(cs);
    }

    public static void swap(char[] cs, int i, int j) {
        char tmp = cs[i];
        cs[i] = cs[j];
        cs[j] = tmp;
    }

    public static void reverse(char[] cs, int i, int j) {
        while (i < j) {
            swap(cs, i, j);
            i++;
            j--;
        }
    }

    private MathUtils() {}
}
