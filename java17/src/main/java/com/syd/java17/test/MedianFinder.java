package com.syd.java17.test;

import java.util.*;

/**
 * @author SYD
 * @description
 * @date 2021/8/27
 */
public class MedianFinder {
    TreeMap<Integer, Integer> nums;
    int left, right;
    /** initialize your data structure here. */
    public MedianFinder() {
        nums = new TreeMap<>();
    }

    public void addNum(int num) {
    }

    public double findMedian() {
        return (nums.get(left) + nums.get(right)) / 2.0;
    }
}

