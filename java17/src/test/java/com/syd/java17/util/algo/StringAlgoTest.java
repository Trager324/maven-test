package com.syd.java17.util.algo;

import com.syd.java17.BaseTest;
import com.syd.java17.framework.TestData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author songyide
 * @date 2022/8/3
 */
class StringAlgoTest extends BaseTest {

    @Test
    void minimumRepresentation() {
        List<TestData<Void, String>> data = List.of(
                TestData.of("abc", "abc"),
                TestData.of("abc", "cab"),
                TestData.of("abc", "bca"),
                TestData.of("aaaaaaaaaaaaaaaaaab", "aaaaaaaaaabaaaaaaaa")
        );
        test(data);
    }

    @Test
    void kmpMatchingTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(0, "ABCDABC", "ABC"),
                TestData.of(9, "ABBAABBBABABBAABBABAAA", "BABBA"),
                TestData.of(15, "BBC ABCDAB ABCDABCDABDE", "ABCDABD"),
                TestData.of(0, "ABCDABC", "ABC"),
                TestData.of(-1, "ABCDABC", "ABD")
        );
        test(data);
    }

    @Test
    void nextPermutationTest() {
        List<TestData<Void, String>> data = List.of(
                TestData.of("ACB", "ABC"),
                TestData.of("BAC", "ACB"),
                TestData.of("BCA", "BAC"),
                TestData.of("CAB", "BCA"),
                TestData.of("CBA", "CAB"),
                TestData.of("ABC", "CBA")
        );
        test(data);
    }

    @Test
    void prevPermutationTest() {
        List<TestData<Void, String>> data = List.of(
                TestData.of("ABC", "ACB"),
                TestData.of("ACB", "BAC"),
                TestData.of("BAC", "BCA"),
                TestData.of("BCA", "CAB"),
                TestData.of("CAB", "CBA"),
                TestData.of("CBA", "ABC")
        );
        test(data);
    }
}