package com.syd.java19.algo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Repeat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListAlgoTest {
    List<Integer> ascList;
    List<Integer> descList;
    Random RND = ThreadLocalRandom.current();

    @BeforeEach
    void beforeEach() {
        // List.of不可修改, Arrays.asList可以交换
        ascList = Arrays.asList(1, 2, 3, 5, 5, 5, 7, 8, 8, 10);
        descList = Arrays.asList(1, 2, 3, 5, 5, 5, 7, 8, 8, 10);
        descList.sort(Comparator.reverseOrder());
    }

    @Test
    @Repeat(10)
    void swap() {
        var size = ascList.size();
        int il = RND.nextInt(size - 1);
        int ir = RND.nextInt(size - il) + il + 1;
        int vl = ascList.get(il), vr = ascList.get(ir);
        ListAlgo.swap(ascList, il, ir);
        assertEquals(vr, ascList.get(il));
        assertEquals(vl, ascList.get(ir));
    }

    @Test
    void lowerBound() {

    }

    @Test
    void upperBound() {

    }

    @Test
    void nthElement() {

    }
}