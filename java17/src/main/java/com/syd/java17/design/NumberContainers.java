package com.syd.java17.design;

import com.syd.java17.struct.Solution;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class NumberContainers {
    Map<Integer, Integer> idxMap = new HashMap<>();
    Map<Integer, TreeSet<Integer>> orderMap = new HashMap<>();

    public NumberContainers() {

    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(Arrays.toString(Solution.invokeResults(NumberContainers.class,
                "[\"NumberContainers\",\"find\",\"change\",\"change\",\"change\",\"change\",\"find\",\"change\",\"find\"]",
                "[[],[10],[2,10],[1,10],[3,10],[5,10],[10],[1,20],[10]]")));
    }

    public void change(int index, int number) {
        Integer last = idxMap.get(index);
        if (last != null) {
            orderMap.get(last).remove(index);
        }
        orderMap.computeIfAbsent(number, k -> new TreeSet<>()).add(index);
        idxMap.put(index, number);
    }

    public int find(int number) {
        TreeSet<Integer> set = orderMap.get(number);
        if (set == null || set.isEmpty()) {
            return -1;
        }
        return set.first();
    }
}