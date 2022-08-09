package com.syd.java17.design;

import java.util.Comparator;
import java.util.TreeSet;

class MyCalendar {
    TreeSet<int[]> booked = new TreeSet<>(Comparator.comparingInt(a -> a[0]));

    public boolean book(int start, int end) {
        if (booked.isEmpty()) {
            booked.add(new int[]{start, end});
            return true;
        }
        int[] tmp = {end, 0}, arr = booked.ceiling(tmp);
        if (arr == booked.first() || booked.lower(tmp)[1] <= start) {
            booked.add(new int[]{start, end});
            return true;
        }
        return false;
    }
}
