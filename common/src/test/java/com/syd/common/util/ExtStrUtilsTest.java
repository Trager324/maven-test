package com.syd.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author songyide
 * @date 2022/8/27
 */
class ExtStrUtilsTest {

    @Test
    void toUnderScoreCase() {
        assertEquals("assert_equal", ExtStrUtils.toUnderScoreCase("assertEqual"));
        assertEquals("hello_world", ExtStrUtils.toUnderScoreCase("helloWorld"));
    }

    @Test
    void toCamelCase() {
        assertEquals("assertEqual", ExtStrUtils.toCamelCase("assert_equal"));
        assertEquals("helloWorld", ExtStrUtils.toCamelCase("HELLO_WORLD"));
    }
}