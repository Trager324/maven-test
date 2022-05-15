package com.syd.java17.utils.algo;

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

    int bsFlooringEqual(List<Integer> list, int x) {
        int l = 0, r = list.size() - 1;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (list.get(m) < x) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    int bsCeilingEqual(List<Integer> list, int x) {
        int l = 0, r = list.size() - 1;
        while (l < r) {
            int m = l + ((r - l + 1) >> 1);
            if (list.get(m) <= x) {
                l = m;
            } else {
                r = m - 1;
            }
        }
        return l;
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

    public static void main(String[] args) {
//        System.out.println(kmpMatch("BBC ABCDAB ABCDABCDABDE", "ABCDABD"));
//        System.out.println(kmpMatch("ABCDABC", "ABC"));
        System.out.println(kmpMatch("ABBAABBBABABBAABBABAAA", "BABBA"));
    }
}
