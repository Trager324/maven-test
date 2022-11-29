package com.syd.java19.util;

import java.util.List;

/**
 * @author songyide
 * @date 2022/11/26
 */
public class ListUtils {

    public static <T> void swap(List<T> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
