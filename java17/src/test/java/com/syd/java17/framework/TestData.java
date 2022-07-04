package com.syd.java17.framework;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author songyide
 * @date 2022/7/3
 */
@Data
@NoArgsConstructor
public class TestData<R, T> {
    T expected;
    R target;
    Object[] args;
    public TestData(R target, T expected, Object... args) {
        this.expected = expected;
        this.args = args;
    }
    public static <R, T> TestData<R, T> of(T expected, Object... args) {
        return new TestData<>(null, expected, args);
    }
}
