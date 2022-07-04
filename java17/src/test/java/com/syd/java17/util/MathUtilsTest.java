package com.syd.java17.util;

import com.syd.java17.framework.MethodTester;
import com.syd.java17.framework.TestData;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

/**
 * @author songyide
 * @date 2022/6/6
 */
public class MathUtilsTest {
    static final Class<?> CLAZZ = MathUtils.class;
    static MethodTester<Void, Integer> GCD_TESTER;
    static MethodTester<Void, Integer> KMP_MATCH_TESTER;
    static MethodTester<Void, Integer> BS_FLOORING_TESTER;
    static MethodTester<Void, Integer> BS_FLOORING_EQUAL_TESTER;
    static MethodTester<Void, Integer> BS_CEILING_TESTER;
    static MethodTester<Void, Integer> BS_CEILING_EQUAL_TESTER;
    static MethodTester<Void, String> NEXT_PERMUTATION_TESTER;
    static MethodTester<Void, String> PREV_PERMUTATION_TESTER;

    static {
        try {
            GCD_TESTER = new MethodTester<>(CLAZZ.getMethod("gcd", int.class, int.class));
            KMP_MATCH_TESTER = new MethodTester<>(CLAZZ.getMethod("kmpMatch", String.class, String.class));
            BS_FLOORING_TESTER = new MethodTester<>(CLAZZ.getMethod("bsFlooring", List.class, Object.class, Comparator.class));
            BS_FLOORING_EQUAL_TESTER = new MethodTester<>(CLAZZ.getMethod("bsFlooringEqual", List.class, Object.class, Comparator.class));
            BS_CEILING_TESTER = new MethodTester<>(CLAZZ.getMethod("bsCeiling", List.class, Object.class, Comparator.class));
            BS_CEILING_EQUAL_TESTER = new MethodTester<>(CLAZZ.getMethod("bsCeilingEqual", List.class, Object.class, Comparator.class));
            NEXT_PERMUTATION_TESTER = new MethodTester<>(CLAZZ.getMethod("nextPermutation", String.class));
            PREV_PERMUTATION_TESTER = new MethodTester<>(CLAZZ.getMethod("prevPermutation", String.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void gcd() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(1, 97, 3),
                TestData.of(12, 24, 36)
        );
        GCD_TESTER.testList(data);
    }

    @Test
    public void bsTest() {
        List<Integer> list = List.of(1, 2, 3, 5, 5, 5, 8, 9);
        List<TestData<Void, Integer>> bsFlooringData = List.of(
                TestData.of(2, list, 5, Comparator.naturalOrder()),
                TestData.of(-1, list, 0, Comparator.naturalOrder()),
                TestData.of(7, list, 10, Comparator.naturalOrder())
        );
        List<TestData<Void, Integer>> bsFlooringEqualData = List.of(
                TestData.of(5, list, 5, Comparator.naturalOrder()),
                TestData.of(-1, list, 0, Comparator.naturalOrder()),
                TestData.of(7, list, 10, Comparator.naturalOrder())
        );
        List<TestData<Void, Integer>> bsCeilingData = List.of(
                TestData.of(6, list, 5, Comparator.naturalOrder()),
                TestData.of(0, list, 0, Comparator.naturalOrder()),
                TestData.of(8, list, 10, Comparator.naturalOrder())
        );
        List<TestData<Void, Integer>> bsCeilingEqualData = List.of(
                TestData.of(3, list, 5, Comparator.naturalOrder()),
                TestData.of(0, list, 0, Comparator.naturalOrder()),
                TestData.of(8, list, 10, Comparator.naturalOrder())
        );
        BS_FLOORING_TESTER.testList(bsFlooringData);
        BS_FLOORING_EQUAL_TESTER.testList(bsFlooringEqualData);
        BS_CEILING_TESTER.testList(bsCeilingData);
        BS_CEILING_EQUAL_TESTER.testList(bsCeilingEqualData);
    }

    @Test
    void kmpMatchingTest() {
        List<TestData<Void, Integer>> data = List.of(
                TestData.of(0, "ABCDABC", "ABC"),
                TestData.of(9, "ABBAABBBABABBAABBABAAA", "BABBA"),
                TestData.of(15, "BBC ABCDAB ABCDABCDABDE", "ABCDABD"),
                TestData.of(0, "ABCDABC", "ABC")
        );
        KMP_MATCH_TESTER.testList(data);
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
        NEXT_PERMUTATION_TESTER.testList(data);
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
        PREV_PERMUTATION_TESTER.testList(data);
    }

}
