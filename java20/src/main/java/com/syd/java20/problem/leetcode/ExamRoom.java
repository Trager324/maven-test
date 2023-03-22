package com.syd.java20.problem.leetcode;

import com.syd.java20.struct.Solution;

import java.util.*;

class ExamRoom {
    TreeSet<Integer> treeSet = new TreeSet<>();
    int n;

    public ExamRoom(int n) {
        this.n = n;
    }
    
    public int seat() {
        if (treeSet.size() == 0) {
            treeSet.add(0);
            return 0;
        }
        var it = treeSet.iterator();
        var first = it.next();
        int m = first, last = first, start = -1;
        while (it.hasNext()) {
            var next = it.next();
            var dist = (next - last) >> 1;
            if (dist > m) {
                m = dist;
                start = last;
            }
            last = next;
        }
        if (n - 1 - last > m) {
            treeSet.add(n - 1);
            return n - 1;
        }
        if (start == -1) {
            treeSet.add(0);
            return 0;
        }
        treeSet.add(start + m);
        return start + m;
    }
    
    public void leave(int p) {
        treeSet.remove(p);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Solution.invokeResults(ExamRoom.class,
                "[\"ExamRoom\",\"seat\",\"seat\",\"seat\",\"seat\",\"leave\",\"seat\"]",
                "[[10],[],[],[],[],[4],[]]"));
        System.out.println(Solution.invokeResults(ExamRoom.class,
                "[\"ExamRoom\",\"seat\",\"seat\",\"seat\",\"seat\",\"leave\",\"leave\",\"seat\"]",
                "[[4],[],[],[],[],[1],[3],[]]"));
        System.out.println(Solution.invokeResults(ExamRoom.class,
                "[\"ExamRoom\",\"seat\",\"seat\",\"seat\",\"leave\",\"leave\",\"seat\",\"seat\",\"seat\",\"seat\",\"seat\",\"seat\",\"seat\",\"seat\",\"seat\",\"leave\"]",
                "[[10],[],[],[],[0],[4],[],[],[],[],[],[],[],[],[],[0]]"));
    }
}
