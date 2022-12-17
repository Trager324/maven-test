package com.syd.common.util;

import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author songyide
 * @date 2022/7/11
 */
public class BatchUtilsTest {
    static int func(List<Void> list) {
        return 1;
    }

    static long func2(List<Void> list) {
        return 1;
    }

    static List<Void> emptyList(int size) {
        return new AbstractList<Void>() {
            @Override
            public Void get(int index) {
                return null;
            }

            @Override
            public int size() {
                return size;
            }
        };
    }

    @Test
    void testBatchInsert() {
        assertEquals(0, BatchUtils.batchInsert(emptyList(0), r -> null));
        assertEquals(0, BatchUtils.batchInsert(null, r -> null));

        assertEquals(0, BatchUtils.batchInsert(emptyList(1000), r -> null));
        assertEquals(0, BatchUtils.batchInsert(emptyList(1000), r -> null, 500));
        assertEquals(0, BatchUtils.batchInsert(emptyList(1000), r -> null, 1));

        assertEquals(1, BatchUtils.batchInsert(emptyList(1000), BatchUtilsTest::func2));
        assertEquals(2, BatchUtils.batchInsert(emptyList(1000), BatchUtilsTest::func2, 500));
        assertEquals(1000, BatchUtils.batchInsert(emptyList(1000), BatchUtilsTest::func2, 1));

        assertEquals(100, BatchUtils.batchInsert(emptyList(1000), BatchUtilsTest::func, 10));
        assertEquals(100, BatchUtils.batchInsert(emptyList(991), BatchUtilsTest::func, 10));
        assertEquals(100, BatchUtils.batchInsert(emptyList(999), BatchUtilsTest::func, 10));
        assertEquals(101, BatchUtils.batchInsert(emptyList(1001), BatchUtilsTest::func, 10));
        assertEquals(101, BatchUtils.batchInsert(emptyList(1009), BatchUtilsTest::func, 10));

        assertEquals(21, BatchUtils.batchInsert(emptyList(2100000000), BatchUtilsTest::func, 100000000));
        assertEquals(22, BatchUtils.batchInsert(emptyList(2100000001), BatchUtilsTest::func, 100000000));
        assertEquals(22, BatchUtils.batchInsert(emptyList(Integer.MAX_VALUE), BatchUtilsTest::func, 100000000));
    }
}
