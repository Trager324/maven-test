package com.syd.java17.design;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

class SmallestInfiniteSet {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    Set<Integer> set = new HashSet<>();

    int offset = 1;

    public SmallestInfiniteSet() {

    }
    
    public int popSmallest() {
        if (!pq.isEmpty()) {
            Integer val = pq.poll();
            set.remove(val);
            return val;
        }
        return offset++;
    }
    
    public void addBack(int num) {
        if (num < offset && !set.contains(num)) {
            pq.add(num);
            set.add(num);
        }
    }
}