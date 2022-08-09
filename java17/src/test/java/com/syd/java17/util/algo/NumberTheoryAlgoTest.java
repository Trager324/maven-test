package com.syd.java17.util.algo;

import com.syd.java17.framework.BaseTest;
import com.syd.java17.framework.TestData;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author songyide
 * @date 2022/8/4
 */
class NumberTheoryAlgoTest extends BaseTest {

    @Test
    public void gcdTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(1, 97, 3),
                TestData.of(12, 24, 36)
        );
        test(data);
    }

    @Test
    void breakdown() {

    }

}