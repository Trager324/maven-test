package com.syd.java17.design;

import java.util.Arrays;

class ATM {
    static final int N = 5;
    static final int[] VALUES = {20, 50, 100, 200, 500};
    long[] banknotes = new long[N];

    public void deposit(int[] banknotesCount) {
        for (int i = 0; i < N; i++) {
            banknotes[i] += banknotesCount[i];
        }
    }

    public int[] withdraw(int amount) {
        int[] res = new int[N];
        int remain = amount;
        for (int i = N - 1; i >= 0; i--) {
            if (remain >= VALUES[i]) {
                int count = (int)Math.min(remain / VALUES[i], banknotes[i]);
                remain -= count * VALUES[i];
                res[i] = count;
            }
        }
        if (remain > 0) {
            return new int[]{-1};
        }
        for (int i = 0; i < N; i++) {
            banknotes[i] -= res[i];
        }
        return res;
    }
}
