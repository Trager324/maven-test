package com.syd.java17.struct;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.syd.java17.struct.Solution.list2Str;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode parseListNode(String s) {
        JSONArray array = JSONArray.parseArray(s);
        ListNode tHead = new ListNode(), p = tHead;
        int n = array.size();
        for (int i = 0; i < n; i++) {
            p = p.next = new ListNode(array.getIntValue(i));
        }
        return tHead.next;
    }

    public static void main(String[] args) {
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>();
        for (ListNode p = this; p != null; p = p.next) {
            list.add(p.val);
        }
        return list2Str(list);
    }
}
