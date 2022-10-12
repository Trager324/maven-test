package com.syd.java19.struct;

class MyHashSet {
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private final float loadFactor = 0.75f;
    private Node[] values;
    private int threshold = 16;
    private int size;

    public MyHashSet() {
        this(16);
    }

    public MyHashSet(int threshold) {
        int n = -1 >>> Integer.numberOfLeadingZeros(threshold - 1);
        this.threshold = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        this.values = new Node[this.threshold];
    }

    public static void main(String[] args) {
        MyHashSet set = new MyHashSet();
        set.add(1);
        set.add(2);
        System.out.println(set.contains(1));
        System.out.println(set.contains(3));
        set.add(2);
        System.out.println(set.contains(2));
        set.remove(2);
        System.out.println(set.contains(2));
    }

    private void resize(int newThreshold) {
        Node[] newValues = new Node[newThreshold];
        int mod = newThreshold - 1;
        for (Node value : values) {
            while (value != null) {
                Node tmp = value.next;
                int newKey = value.val & mod;
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

    public void add(int key) {
        int idx = key & (threshold - 1);
        if (values[idx] == null) {
            values[idx] = new Node(key);
            return;
        }
        Node n = values[idx];
        if (n.val == key) return;
        while (n.next != null) {
            if (n.next.val == key) return;
            n = n.next;
        }
        n.next = new Node(key);
        if (++size > threshold * loadFactor) {
            resize(threshold << 1);
        }
    }

    public void remove(int key) {
        int idx = key & (threshold - 1);
        Node tmp = new Node(0, values[idx]), n = tmp;
        while (n.next != null) {
            if (n.next.val == key) {
                n.next = n.next.next;
                break;
            }
            n = n.next;
        }
        values[idx] = tmp.next;
    }

    public boolean contains(int key) {
        int idx = key & (threshold - 1);
        Node n = values[idx];
        while (n != null) {
            if (n.val == key) {
                return true;
            }
            n = n.next;
        }
        return false;
    }

    public int size() {return size;}

    private static class Node {
        int val;
        Node next;

        Node() {}

        Node(int v) {val = v;}

        Node(int v, Node n) {
            val = v;
            next = n;
        }
    }
}


