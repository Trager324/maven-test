package com.syd.java20.algo.struct;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author songyide
 * @date 2022/11/7
 */
class BITTest {
    static List<Integer> list;
    static BIT tree;

    @BeforeAll
    static void setup() {
        list = IntStream.rangeClosed(1, 100).boxed().toList();
        tree = new BIT(list.size());
        for (int i = 0; i < list.size(); i++) {
            tree.modify(i + 1, list.get(i));
        }
    }

    @Test
    void test() {
        for (int i = 1; i <= list.size(); i++) {
            assertEquals((long)i * (i + 1) >> 1, tree.query(i), i + "行");
        }
    }
}