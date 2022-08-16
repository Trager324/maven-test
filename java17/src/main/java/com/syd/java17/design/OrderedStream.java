package com.syd.java17.design;

import java.util.ArrayList;
import java.util.List;

class OrderedStream {
    String[] list;
    int ptr = 0;

    public OrderedStream(int n) {
        list = new String[n];
    }

    public List<String> insert(int idKey, String value) {
        list[idKey - 1] = value;
        List<String> res = new ArrayList<>();
        int i;
        for (i = ptr; i < list.length; i++) {
            if (list[i] != null) {
                res.add(list[i]);
            } else {
                break;
            }
        }
        ptr = i;
        return res;
    }
}