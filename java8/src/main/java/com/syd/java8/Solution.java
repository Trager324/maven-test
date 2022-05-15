package com.syd.java8;

import java.util.Arrays;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

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
        AbstractQueuedSynchronizer aqs;
        return res;
    }
}
