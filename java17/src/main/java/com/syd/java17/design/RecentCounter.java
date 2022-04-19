package com.syd.java17.design;

import java.util.LinkedList;
import java.util.Queue;

class RecentCounter {
    Queue<Integer> q = new LinkedList<>();
    RecentCounter() {}
    public int ping(int t) {
        while (!q.isEmpty()) {
            if (q.peek() + 3000 >= t) {
                break;
            }
            q.poll();
        }
        q.offer(t);
        return q.size();
    }
}
