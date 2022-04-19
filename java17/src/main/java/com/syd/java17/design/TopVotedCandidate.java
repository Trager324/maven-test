package com.syd.java17.design;

import java.util.TreeMap;

class TopVotedCandidate {
    private final TreeMap<Integer, Integer> orderMap = new TreeMap<>();
    public TopVotedCandidate(int[] persons, int[] times) {
        int[] countMap = new int[5001];
        int n = persons.length, m = 0;
        for (int i = 0; i < n; i++) {
            int person = persons[i], cnt = ++countMap[person];
            m = cnt >= countMap[m] ? person : m;
            orderMap.put(times[i], m);
        }
    }
    public int q(int t) {
        return orderMap.floorEntry(t).getValue();
    }
}
