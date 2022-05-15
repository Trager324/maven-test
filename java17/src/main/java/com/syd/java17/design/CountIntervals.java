package com.syd.java17.design;

import java.util.ArrayList;
import java.util.List;

class CountIntervals {
    List<int[]> list = new ArrayList<>();

    int bsFlooringEqual(List<int[]> list, int x) {
        int l = 0, r = list.size() - 1;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (list.get(m)[0] < x) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    public void add(int left, int right) {
        int[] item = new int[]{left, right};
        if (list.size() == 0) {
            list.add(item);
            return;
        }
        int idx = bsFlooringEqual(list, left);
        if (left <= list.get(idx)[1] + 1) {
            if (idx < list.size() - 1 && list.get(idx + 1)[0] <= right + 1) {
                list.get(idx)[1] = Math.max(right, list.get(idx + 1)[1]);
                list.remove(idx + 1);
            } else {
                list.get(idx)[1] = Math.max(right, list.get(idx)[1]);
            }
        } else {
            if (idx < list.size() - 1 && list.get(idx + 1)[0] <= right + 1) {
                list.get(idx + 1)[0] = left;
                list.get(idx + 1)[1] = Math.max(right, list.get(idx + 1)[1]);
            } else {
                list.add(idx, item);
            }
        }
    }

    public int count() {
        int res = 0;
        for (int[] item : list) {
            res += item[1] - item[0] + 1;
        }
        return res;
    }
}
