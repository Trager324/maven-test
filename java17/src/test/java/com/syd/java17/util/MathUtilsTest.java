package com.syd.java17.util;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author songyide
 * @date 2022/6/6
 */
public class MathUtilsTest {
    @Test
    public void gcd() {
        assertEquals(1, MathUtils.gcd(97, 3));
        assertEquals(12, MathUtils.gcd(24, 36));
    }

    @Test
    public void bsTest() {
        List<Integer> list = List.of(1, 2, 3, 5, 5, 5, 8, 9);
        // 等值测试
        assertEquals(2, MathUtils.bsFlooring(list, 5, Comparator.naturalOrder()));
        assertEquals(5, MathUtils.bsFlooringEqual(list, 5, Comparator.naturalOrder()));
        assertEquals(6, MathUtils.bsCeiling(list, 5, Comparator.naturalOrder()));
        assertEquals(3, MathUtils.bsCeilingEqual(list, 5, Comparator.naturalOrder()));
        // 边界测试
        assertEquals(-1, MathUtils.bsFlooring(list, 0, Comparator.naturalOrder()));
        assertEquals(7, MathUtils.bsFlooring(list, 10, Comparator.naturalOrder()));
        assertEquals(-1, MathUtils.bsFlooringEqual(list, 0, Comparator.naturalOrder()));
        assertEquals(7, MathUtils.bsFlooringEqual(list, 10, Comparator.naturalOrder()));
        assertEquals(0, MathUtils.bsCeiling(list, 0, Comparator.naturalOrder()));
        assertEquals(8, MathUtils.bsCeiling(list, 10, Comparator.naturalOrder()));
        assertEquals(0, MathUtils.bsCeilingEqual(list, 0, Comparator.naturalOrder()));
        assertEquals(8, MathUtils.bsCeilingEqual(list, 10, Comparator.naturalOrder()));
    }
}
