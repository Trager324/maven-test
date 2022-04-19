package com.syd.java17.design;

import java.util.*;

class StockPrice {

    TreeMap<Integer, List<Integer>> map = new TreeMap<>();
    int currentKey, currentValue;

    public StockPrice() {

    }

    public void update(int timestamp, int price) {
        if (map.size() == 0) {
            currentKey = timestamp;
            currentValue = price;
        }
        List<Integer> list = map.getOrDefault(price, new ArrayList<>());

    }

    public int current() {
        return currentValue;
    }

    public int maximum() {
        return map.lastKey();
    }

    public int minimum() {
        return map.firstKey();
    }

    public static void main(String[] args) {
        StockPrice sp = new StockPrice();
        sp.update(1, 10);
        sp.update(2, 5);
        sp.update(1, 3);
        System.out.println(sp.maximum());
    }
}
