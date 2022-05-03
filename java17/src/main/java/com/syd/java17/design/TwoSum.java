package com.syd.java17.design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class TwoSum {
    Map<Integer, Integer> map = new HashMap<>();
    
    public void add(int number) {
        map.put(number, map.getOrDefault(number, 0) + 1);
    }
    
    public boolean find(int value) {
        for (var entry : map.entrySet()) {
            int i = entry.getKey();
            if (i == value - i) {
                if (entry.getValue() > 1) return true;
            } else if (map.containsKey(value - i)) {
                return true;
            }
        }
        return false;
    }
}