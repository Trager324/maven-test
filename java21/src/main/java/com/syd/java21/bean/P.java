package com.syd.java21.bean;

/**
 * @author songyide
 * @date 2022/11/8
 */
public record P<T extends Number>(T x, T y) {
}
