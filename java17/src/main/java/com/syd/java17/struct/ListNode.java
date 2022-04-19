package com.syd.java17.struct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;

import static com.syd.java17.struct.Solution.listToString;

public class ListNode implements JSONAware {
    public int val;
    public ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
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

    @Override
    public String toString() {
        List<Integer> list = new ArrayList<>();
        for (ListNode p = this; p != null; p = p.next) {
            list.add(p.val);
        }
        return listToString(list);
    }

    @Override
    public String toJSONString() {
        return toJSONString(true);
    }

    public String toJSONString(boolean briefly) {
        StringBuilder sb = new StringBuilder(briefly ? "{v:" : "{val:").append(val);
        if (next != null) sb.append(briefly ? ",n:" : ",next:").append(next.toJSONString(briefly));
        return sb.append("}").toString();
    }

    public static void main(String[] args) {
        ListNode node = parseListNode("[1,2,3,6,5]");
        String jsonStr = node.toJSONString(false);
        System.out.println(node);
        System.out.println(jsonStr);
        ListNode parsed = JSON.parseObject(jsonStr, ListNode.class);
        System.out.println(parsed.toString());
        parsed = JSON.parseObject(jsonStr, new TypeReference<>() {
        });
        System.out.println(parsed.toString());
    }
}
