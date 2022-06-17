package com.syd.java17.test;

import java.util.Random;

/**
 * @author songyide
 * @date 2022/6/17
 */
public class FeatureSummary {
    static final Random RANDOM = new Random();


    /**
     * @since 10
     */
    void _10() {
        var a = "1";
        System.out.println(a.getClass());
    }


    /**
     * @since 13
     */
    void _13() {
        String text = """
                SELECT * FROM users
                WHERE id = ?
                """;
        System.out.println(text);
    }


    /**
     * @since 14
     */
    void _14() {
        Object o = String.valueOf(RANDOM.nextBoolean() ? String.valueOf(RANDOM.nextInt(5)) : 1);
        if (o instanceof String s) {
            switch (s) {
                case "1" -> System.out.println("1");
                case "2" -> System.out.println("2");
                default -> System.out.println("default");
            }
        }
    }


    /**
     * @since 16
     */
    record A(int a) {}


    /**
     * @since 17
     */
    sealed interface B permits C {}

    static non-sealed private class C implements B {}

    void _17() {
        Object o = RANDOM.nextBoolean() ? "1" : null;
        o = new A(1);
        switch (o) {
            case null -> System.out.println("null");
            case String s -> System.out.println(s);
            default -> System.out.println();
        }
    }


    public static void main(String[] args) {
        FeatureSummary summary = new FeatureSummary();
        summary._10();
        summary._13();
        summary._14();
        summary._17();
    }
}
