package com.syd.java17.util.algo;

import com.syd.java17.framework.BaseTest;
import com.syd.java17.framework.TestData;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author songyide
 * @date 2022/8/4
 */
class ListAlgoTest extends BaseTest {
    List<Integer> list = List.of(1, 2, 3, 5, 5, 5, 8, 9);

    @Test
    public void lowerBoundTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(3, list, 5),
                TestData.of(0, list, 0),
                TestData.of(8, list, 10)
        );
        test(data);
    }

    @Test
    public void upperBoundTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(5, list, 5),
                TestData.of(-1, list, 0),
                TestData.of(7, list, 10)
        );
        test(data);
    }

}