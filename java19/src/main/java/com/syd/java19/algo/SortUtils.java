package com.syd.java19.algo;

/**
 * 排序算法包
 *
 * @author syd
 * @date 2021/10/11
 */
public class SortUtils {
    static final int THRESHOLD = 10_000_000;

    static public void bucketSort(int[] arr) {
        int l = Integer.MAX_VALUE, r = Integer.MIN_VALUE, off = 0;
        for (int i : arr) {
            if (i < l) {
                l = i;
            }
            if (i > r) {
                r = i;
            }
        }
        if (r - l > THRESHOLD) {
            throw new OutOfMemoryError();
        }
        int[] buckets;
        if (r > THRESHOLD && l >= 0) {
            buckets = new int[r + 1];
        } else {
            buckets = new int[r - l + 1];
            off = l;
        }
        for (int num : arr) {
            buckets[num - off]++;
        }
        int j = 0;
        for (int i = l; i <= r; i++) {
            while (buckets[i - off]-- > 0) {
                arr[j++] = i;
            }
        }
    }
}
