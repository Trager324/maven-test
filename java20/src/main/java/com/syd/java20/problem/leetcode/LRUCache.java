package com.syd.java20.problem.leetcode;

import java.util.LinkedHashMap;

class LRUCache {
    private final int capacity;
    private final LinkedHashMap<Integer, Integer> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(capacity << 1, 0.75f, true);
    }

    public int get(int key) {return map.getOrDefault(key, -1);}

    public void put(int key, int value) {
        if (!map.containsKey(key) && map.size() >= capacity) map.remove(map.keySet().iterator().next());
        map.put(key, value);
    }
}