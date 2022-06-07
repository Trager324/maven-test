package com.syd.java17.util;

import com.syd.java17.util.algo.MathUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author songyide
 * @date 2022/6/6
 */
public class MathUtilsTest {
    @Test
    public void gcd() {
        Assert.assertEquals(1, MathUtils.gcd(97, 3));
        Assert.assertEquals(12, MathUtils.gcd(24, 36));
    }

    @Test
    public void bsTest() {
        List<Integer> list = List.of(1,2,3,5,5,5,8,9);
        // 等值测试
        Assert.assertEquals(2, MathUtils.bsFlooring(list, 5));
        Assert.assertEquals(5, MathUtils.bsFlooringEqual(list, 5));
        Assert.assertEquals(6, MathUtils.bsCeiling(list, 5));
        Assert.assertEquals(3, MathUtils.bsCeilingEqual(list, 5));
        // 边界测试
        Assert.assertEquals(-1, MathUtils.bsFlooring(list, 0));
        Assert.assertEquals(7, MathUtils.bsFlooring(list, 10));
        Assert.assertEquals(-1, MathUtils.bsFlooringEqual(list, 0));
        Assert.assertEquals(7, MathUtils.bsFlooringEqual(list, 10));
        Assert.assertEquals(0, MathUtils.bsCeiling(list, 0));
        Assert.assertEquals(8, MathUtils.bsCeiling(list, 10));
        Assert.assertEquals(0, MathUtils.bsCeilingEqual(list, 0));
        Assert.assertEquals(8, MathUtils.bsCeilingEqual(list, 10));
    }
}
