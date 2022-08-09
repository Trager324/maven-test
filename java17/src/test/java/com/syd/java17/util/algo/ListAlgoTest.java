package com.syd.java17.util.algo;

import com.syd.java17.framework.BaseTest;
import com.syd.java17.framework.TestData;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

/**
 * @author songyide
 * @date 2022/8/4
 */
class ListAlgoTest extends BaseTest {
    List<Integer> list = List.of(1, 2, 3, 5, 5, 5, 8, 9);

    @Test
    public void bsFlooringTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(2, list, 5, Comparator.naturalOrder()),
                TestData.of(-1, list, 0, Comparator.naturalOrder()),
                TestData.of(7, list, 10, Comparator.naturalOrder())
        );
        test(data);
    }

    @Test
    public void bsFlooringEqualTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(5, list, 5, Comparator.naturalOrder()),
                TestData.of(-1, list, 0, Comparator.naturalOrder()),
                TestData.of(7, list, 10, Comparator.naturalOrder())
        );
        test(data);
    }

    @Test
    public void bsCeilingTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(6, list, 5, Comparator.naturalOrder()),
                TestData.of(0, list, 0, Comparator.naturalOrder()),
                TestData.of(8, list, 10, Comparator.naturalOrder())
        );
        test(data);
    }

    @Test
    public void bsCeilingEqualTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(3, list, 5, Comparator.naturalOrder()),
                TestData.of(0, list, 0, Comparator.naturalOrder()),
                TestData.of(8, list, 10, Comparator.naturalOrder())
        );
        test(data);
    }
}