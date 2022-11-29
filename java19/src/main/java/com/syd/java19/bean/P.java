package com.syd.java19.bean;

/**
 * @author songyide
 * @date 2022/11/8
 */
public record P<T extends Number>(T x, T y) {
}
