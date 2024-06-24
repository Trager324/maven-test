package org.behappy.java17.design;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.behappy.java17.struct.Solution.invokeResults;

class AllOne {
    Map<String, Node> map = new HashMap<>();
    Node head = new Node(""), tail = new Node("");
    public AllOne() {
        head.next = tail;
        tail.prev = head;
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(Arrays.toString(invokeResults(
                AllOne.class,
                "[\"AllOne\",\"inc\",\"inc\",\"inc\",\"inc\",\"getMaxKey\",\"inc\",\"inc\",\"inc\",\"dec\",\"inc\",\"inc\",\"inc\",\"getMaxKey\"]",
                "[[],[\"hello\"],[\"goodbye\"],[\"hello\"],[\"hello\"],[],[\"leet\"],[\"code\"],[\"leet\"],[\"hello\"],[\"leet\"],[\"code\"],[\"code\"],[]]"
        )));
        System.out.println(Arrays.toString(invokeResults(
                AllOne.class,
                "[\"AllOne\",\"inc\",\"inc\",\"inc\",\"inc\",\"inc\",\"inc\",\"getMaxKey\",\"inc\",\"dec\",\"getMaxKey\",\"dec\",\"inc\",\"getMaxKey\",\"inc\",\"inc\",\"dec\",\"dec\",\"dec\",\"dec\",\"getMaxKey\",\"inc\",\"inc\",\"inc\",\"inc\",\"inc\",\"inc\",\"getMaxKey\",\"getMinKey\"]",
                "[[],[\"hello\"],[\"world\"],[\"leet\"],[\"code\"],[\"ds\"],[\"leet\"],[],[\"ds\"],[\"leet\"],[],[\"ds\"],[\"hello\"],[],[\"hello\"],[\"hello\"],[\"world\"],[\"leet\"],[\"code\"],[\"ds\"],[],[\"new\"],[\"new\"],[\"new\"],[\"new\"],[\"new\"],[\"new\"],[],[]]"
        )));
    }

    public void inc(String key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.cnt++;
            while (node.next != tail && node.next.cnt < node.cnt) {
                Node next = node.next;
                next.prev = node.prev;
                node.prev.next = next;
                node.next = next.next;
                next.next.prev = node;
                next.next = node;
                node.prev = next;
            }
        } else {
            Node node = new Node(key);
            node.next = head.next;
            node.next.prev = node;
            node.prev = head;
            head.next = node;
            map.put(key, node);
        }
    }

    public void dec(String key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            if (node.cnt-- == 1) {
                Node prev = node.prev;
                node.prev.next = node.next;
                node.next.prev = prev;
                map.remove(key);
            } else {
                while (node.prev != head && node.prev.cnt > node.cnt) {
                    Node prev = node.prev;
                    prev.next = node.next;
                    node.next.prev = prev;
                    node.prev = prev.prev;
                    prev.prev.next = node;
                    prev.prev = node;
                    node.next = prev;
                }
            }
        }
    }

    public String getMaxKey() {
        return tail.prev.value;
    }

    public String getMinKey() {
        return head.next.value;
    }

    static class Node {
        int cnt;
        String value;
        Node next, prev;

        Node(String s) {
            cnt = 1;
            value = s;
        }
    }
}
