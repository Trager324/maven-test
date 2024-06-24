package org.behappy.java17.util.algo;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.behappy.java17.util.algo.MathAlgo.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author songyide
 * @date 2022/6/6
 */
public class MathAlgoTest {
    @Test
    void testCombine() {
        int[][] data = {
                {4, 2}, {6, 2}, {6, 3}, {6, 4}, {10, 2}, {99, 44}, {99, 45}, {99, 46}, {99, 47}, {99, 48}, {99, 49},
        };
        for (int i =0; i < data.length; i++) {
            int[] item = data[i];
            int n = item[0], m = item[1];
            BigInteger mod = combineBigInteger(n, m).mod(BigInteger.valueOf(MOD));
            int combine = combine(n, m);
            assertEquals(mod.intValue(), combine, "at row " + i);
        }
    }

    @Test
    void binPow() {
    }
}
