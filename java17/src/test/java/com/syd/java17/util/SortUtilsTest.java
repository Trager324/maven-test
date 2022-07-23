package com.syd.java17.util;

import com.syd.java17.framework.MethodTester;
import com.syd.java17.framework.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author songyide
 * @date 2022/7/4
 */
public class SortUtilsTest {

    static int[] arr1 = {5, 8, 4, 7, 3, 6, 2, 1, 0, 9};
    static int[] res1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static int[] arr2 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1};
    static int[] res2 = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void bucketSort() {
        int[] a = Arrays.copyOf(arr1, arr1.length);
        SortUtils.bucketSort(a);
        assertArrayEquals(res1, a);
        a = Arrays.copyOf(arr2, arr2.length);
        SortUtils.bucketSort(a);
        assertArrayEquals(res2, a);
    }
}
