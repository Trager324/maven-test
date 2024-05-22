package com.syd.java.java;


import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

public class ComparatorTest {
    @Test
    void Random209() {
        Random rnd = new Random(209);
        var list = IntStream.range(0, 32)
                .mapToObj(i -> rnd.nextInt())
                .toList();
        var sorted = list.stream()
                .sorted((i1, i2) -> i1 - i2)
                .toList();
    }

    @Test
    void Random2664() {
        Random rnd = new Random(2664);
        var list = IntStream.range(0, 32)
                .mapToObj(i -> rnd.nextInt(1000, 1100))
                .toList();
        var sorted = list.stream()
                .sorted((i1, i2) -> i1 < i2 ? -1 : i1 == i2 ? 0 : 1)
                .toList();
    }
}
