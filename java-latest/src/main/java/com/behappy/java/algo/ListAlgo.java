package com.behappy.java.algo;

import java.util.Comparator;
import java.util.List;

/**
 * @author songyide
 * @date 2022/8/4
 */
public class ListAlgo {
    /**
     * 交换列表元素
     *
     * @param list 列表
     * @param i    索引i
     * @param j    索引j
     */
    public static <T> void swap(List<T> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    /**
     * 二分搜索，升序列表，大于等于x的第一个元素的位置
     */
    public static <T extends Comparable<T>> int lowerBound(List<? extends T> list, T x) {
        return lowerBound(list, x, Comparator.naturalOrder());
    }

    /**
     * 二分搜索，升序列表，大于等于x的最后一个元素的位置
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
            if (cmp.compare(list.get(m), x) < 0)
                l = m;
            else
                r = m;
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
            if (cmp.compare(list.get(m), x) <= 0)
                l = m;
            else
                r = m;
        }
        return l;
    }

    /**
     * 快速选择算法
     */
    public static <T extends Comparable<T>> void nthElement(List<T> list, int left, int right, int kth) {
        if (left == right)
            return;

        int pivot = (int) (left + Math.random() * (right - left + 1));
        swap(list, pivot, right);
        // 三路划分（three-way partition）
        int sepl = left - 1, sepr = left - 1;
        for (int i = left; i <= right; i++) {
            if (list.get(i).compareTo(list.get(right)) > 0) {
                swap(list, ++sepr, i);
                swap(list, ++sepl, sepr);
            } else if (list.get(i).equals(list.get(right)))
                swap(list, ++sepr, i);
        }
        if (sepl < left + kth && left + kth <= sepr)
            return;

        if (left + kth <= sepl)
            nthElement(list, left, sepl, kth);
        else
            nthElement(list, sepr + 1, right, kth - (sepr - left + 1));
    }
}
