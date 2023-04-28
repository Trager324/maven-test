package com.syd.java21.algo;

/**
 * @author syd
 */
public class ArrayAlgo {
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
