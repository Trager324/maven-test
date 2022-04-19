package com.syd.java17.utils.algo;

/**
 * @author SYD
 * @description 排序工具类
 * @date 2021/10/11
 */
public class SortUtils {
    static public void bucketSort(int[] arr) {
        int n = arr.length, beg = arr[0], end = beg;
        int[] buckets = new int[100001];
        buckets[beg]++;
        for (int i = 1; i < n; i++) {
            int num = arr[i];
            buckets[num]++;
            if (num > end) {
                end = num;
            } else if (num < beg) {
                beg = num;
            }
        }
        int j = 0;
        for (int i = beg; i <= end; i++) {
            while (buckets[i]-- > 0) {
                arr[j++] = i;
            }
        }
    }
    static public void bucketSort(int[] arr, int upper) {
        int n = arr.length, beg = arr[0], end = beg;
        int[] buckets = new int[upper + 1];
        buckets[beg]++;
        for (int i = 1; i < n; i++) {
            int num = arr[i];
            buckets[num]++;
            if (num > end) {
                end = num;
            } else if (num < beg) {
                beg = num;
            }
        }
        int j = 0;
        for (int i = beg; i <= end; i++) {
            while (buckets[i]-- > 0) {
                arr[j++] = i;
            }
        }
    }
}
