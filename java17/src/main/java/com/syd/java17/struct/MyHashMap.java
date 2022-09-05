package com.syd.java17.struct;

import java.util.concurrent.atomic.AtomicInteger;

class MyHashMap {
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    private final float loadFactor = 0.75f;
    private Node[] values;
    private int threshold = 16;
    private int size;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MyHashMap(int threshold) {
        int n = -1 >>> Integer.numberOfLeadingZeros(threshold - 1);
        this.threshold = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        this.values = new Node[this.threshold];
    }

    public static void main(String[] args) {
    }

    private void resize(int newThreshold) {
        Node[] newValues = new Node[newThreshold];
        int mod = newThreshold - 1;
        for (Node value : values) {
            while (value != null) {
                Node tmp = value.next;
                int newKey = value.key & mod;
                if (newValues[newKey] == null) {
                    newValues[newKey] = value;
                } else {
                    Node n = newValues[newKey];
                    while (n.next != null) {
                        n = n.next;
                    }
                    n.next = value;
                }
                value.next = null;
                value = tmp;
            }
        }
        values = newValues;
        threshold = newThreshold;
    }

    public void put(int key, int value) {
        int idx = key & (threshold - 1);
        Node tmp = new Node(0, 0, values[idx]), n = tmp;
        while (n.next != null) {
            if (n.next.key == key) {
                n.next.val = value;
                break;
            }
            n = n.next;
        }
        if (n.next == null) {
            n.next = new Node(key, value);
        }
        values[idx] = tmp.next;
        if (++size > threshold * loadFactor) {
            resize(threshold << 1);
        }
    }

    public int get(int key) {
        int idx = key & (threshold - 1);
        Node n = values[idx];
        while (n != null) {
            if (n.key == key) {
                return n.val;
            }
            n = n.next;
        }
        return -1;
    }

    public void remove(int key) {
        int idx = key & (threshold - 1);
        Node tmp = new Node(0, 0, values[idx]), n = tmp;
        while (n.next != null) {
            if (n.next.key == key) {
                n.next = n.next.next;
                break;
            }
            n = n.next;
        }
        values[idx] = tmp.next;
        size--;
    }

    public boolean contains(int key) {
        return get(key) != -1;
    }

    public int size() {return size;}

    private static class Node {
        int key, val;
        Node next;

        Node() {}

        Node(int k, int v) {
            key = k;
            val = v;
        }

        Node(int k, int v, Node n) {
            key = k;
            val = v;
            next = n;
        }
    }

    /**
     * @author asus
     */
    public static class Test {
        public static void main(String[] args) {
            new AtomicInteger().incrementAndGet();

        }
    }
}
