package com.syd.java17.util.algo;

/**
 * @author songyide
 * @date 2022/8/3
 */
public class StringAlgo {
    public static String minimumRepresentation(String s) {
        char[] cs = s.toCharArray();
        int k = 0, i = 0, j = 1, n = cs.length;
        while (k < n && i < n && j < n) {
            if (cs[(i + k) % n] == cs[(j + k) % n]) {
                k++;
            } else {
                if (cs[(i + k) % n] > cs[(j + k) % n]) {
                    i = i + k + 1;
                } else {
                    j = j + k + 1;
                }
                if (i == j) {
                    i++;
                }
                k = 0;
            }
        }
        i = Math.min(i, j);
        return s.substring(i) + s.substring(0, i);
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

    public static int kmpMatching(String s, String t) {
        char[] cs = s.toCharArray(), ct = t.toCharArray();
        int ns = cs.length, nt = ct.length;
        if (ns < nt) {
            return -1;
        }
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
}
