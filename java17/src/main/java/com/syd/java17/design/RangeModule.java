package com.syd.java17.design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class RangeModule {
    Map<Integer, Integer> seg = new HashMap<>();
    Set<Integer> set = new HashSet<>();
    static final int lm = 1, rm = 1_000_000_000;

    public RangeModule() {}

    void build(int l, int r, int ll, int rr, int idx) {

        int m = ll + ((rr - ll) >> 1);
        if (l <= m && m <= r) {
            seg.put(idx, m);
            set.add(m);
        } else if (l <= m) {
            build(l, r, ll, m, idx << 1);
        } else {
            build(l, r, m + 1, rr, idx << 1 | 1);
        }
    }

    public void addRange(int left, int right) {

    }

//    public boolean queryRange(int left, int right) {
//
//    }

    public void removeRange(int left, int right) {

    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */