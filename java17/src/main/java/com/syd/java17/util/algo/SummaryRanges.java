package com.syd.java17.util.algo;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

class SummaryRanges {
    TreeMap<Integer, Integer> map = new TreeMap<>();
    public void addNum(int val) {
        Map.Entry<Integer, Integer> floor = map.floorEntry(val), ceil = map.ceilingEntry(val);
        if (floor != null && floor.getValue() >= val) {
            return;
        }
        int key = val, value = val;
        if (floor != null && floor.getValue() == val - 1) {
            key = floor.getKey();
            map.remove(floor.getKey());
        }
        if (ceil != null && ceil.getKey() == val + 1) {
            value = ceil.getValue();
            map.remove(ceil.getKey());
        }
        map.put(key, value);
    }
    public int[][] getIntervals() {
        int n = map.size();
        int[][] res = new int[n][2];
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        for (int i = 0; i < n; i++) {
            Map.Entry<Integer, Integer> entry = it.next();
            res[i][0] = entry.getKey();
            res[i][1] = entry.getValue();
        }
        return res;
    }
}
