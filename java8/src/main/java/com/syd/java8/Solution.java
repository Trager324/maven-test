package com.syd.java8;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.function.Consumer;

/**
 * @author SYD
 * @description
 * @date 2022/3/20
 */
public class Solution {
    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {
        int n = aliceArrows.length;
        double[][] weights = new double[n][2];
        for (int i = 0; i < n; i++) {
            weights[i][0] = i;
            weights[i][1] = (double)i / aliceArrows[i];
        }
        int[] res = new int[n];
        Arrays.sort(weights, (p, q) -> Double.compare(q[1], p[1]));
        for (int i = 0; i < n && numArrows != 0; i++) {
            int index = (int)weights[i][0], threshold = aliceArrows[index];
            if (numArrows >= threshold) {
                numArrows -= threshold;
                res[index] = threshold;
            }
        }
        return res;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Optional.of(Solution.class.getDeclaredMethod("test")).ifPresent(m -> {
            m.setAccessible(true);
        });
    }
}
