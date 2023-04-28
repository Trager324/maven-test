package com.syd.java20;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class RecordDeconstructTest {
    @Test
    void testSwitchPattern() {
        I19 i = new I19[]{null, new Circle(new P(0, 0), 1),}
                [ThreadLocalRandom.current().nextInt(2)];
        var x = switch (i) {
            case null -> 0;
            case A19 a -> a.hashCode();
            case B19 b -> b.hashCode();
            case Circle(P(var x0, var y0), var r) when r < x0 -> {
                System.out.println(r);
                yield y0;
            }
            case Circle(var p, var r) -> r + Math.abs(p.x) + Math.abs(p.y);
        };
    }

    sealed interface I19 permits A19, B19, Circle {}

    record P(int x, int y) {}

    record Circle(P c, int r) implements I19 {}

    static non-sealed private class A19 implements I19 {}

    static non-sealed private class B19 implements I19 {}
}
