package com.syd.java19.algo;

import java.util.Comparator;
import java.util.List;

/**
 * @author songyide
 * @date 2022/8/4
 */
public class ListAlgo {
    /**
     * 二分搜索，升序列表，严格小于x
     */
    public static <T extends Comparable<T>> int lowerBound(List<? extends T> list, T x) {
        return lowerBound(list, x, Comparator.naturalOrder());
    }

    /**
     * 二分搜索，升序列表，严格小于x
     */
    public static <T extends Comparable<T>> int upperBound(List<? extends T> list, T x) {
        return upperBound(list, x, Comparator.naturalOrder());
    }

    /**
     * 二分搜索，升序列表，大于等于x的第一个元素的位置
     *
     * @param list 按照cmp升序排序的列表
     * @param x    搜索值
     * @param cmp  比较器
     */
    public static <T> int lowerBound(List<? extends T> list, T x, Comparator<? super T> cmp) {
        int l = -1, r = list.size();
        while (l != r - 1) {
            int m = l + ((r - l) >> 1);
            if (cmp.compare(list.get(m), x) < 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return r;
    }

    /**
     * 二分搜索，升序列表，大于等于x的最后一个元素的位置
     *
     * @param list 按照cmp升序排序的列表
     * @param x    搜索值
     * @param cmp  比较器
     */
    public static <T> int upperBound(List<? extends T> list, T x, Comparator<? super T> cmp) {
        int l = -1, r = list.size();
        while (l != r - 1) {
            int m = l + ((r - l) >> 1);
            if (cmp.compare(list.get(m), x) <= 0) {
                l = m;
            } else {
                r = m;
            }
        }
        return l;
    }

    public static void nthElement(List<Integer> results, int left, int kth, int right) {
        if (left == right) {
            return;
        }
        int pivot = (int)(left + Math.random() * (right - left + 1));
        swap(results, pivot, right);
        // 三路划分（three-way partition）
        int sepl = left - 1, sepr = left - 1;
        for (int i = left; i <= right; i++) {
            if (results.get(i) > results.get(right)) {
                swap(results, ++sepr, i);
                swap(results, ++sepl, sepr);
            } else if (results.get(i).equals(results.get(right))) {
                swap(results, ++sepr, i);
            }
        }
        if (sepl < left + kth && left + kth <= sepr) {
            return;
        }
        if (left + kth <= sepl) {
            nthElement(results, left, kth, sepl);
        } else {
            nthElement(results, sepr + 1, kth - (sepr - left + 1), right);
        }
    }

    public static <T> void swap(List<T> results, int index1, int index2) {
        T temp = results.get(index1);
        results.set(index1, results.get(index2));
        results.set(index2, temp);
    }
}
