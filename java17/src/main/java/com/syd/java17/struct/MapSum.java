package com.syd.java17.struct;

class MapSum {
    MapSum[] tries;
    int v;

    public MapSum() {}



    public void insert(char[] cs, int val, int offset) {
        if (tries == null) {
            tries = new MapSum[26];
        }
        if (offset == cs.length) {
            v = val;
        } else {
            int i = cs[offset] - 'a';
            if (tries[i] == null) {
                tries[i] = new MapSum();
            }
            tries[i].insert(cs, val, offset + 1);
        }
    }

    public void insert(String key, int val) {
        insert(key.toCharArray(), val, 0);
    }

    public int sum(String prefix) {
        return sum(prefix.toCharArray(), 0);
    }

    public int sum(char[] cs, int offset) {
        if (offset == cs.length) {
            return subSum();
        } else {
            int i = cs[offset] - 'a';
            if (tries == null || tries[i] == null) {
                return 0;
            }
            return tries[i].sum(cs, offset + 1);
        }
    }

    public int subSum() {
        int s = v;
        if (tries != null) {
            for (MapSum trie : tries) {
                if (trie != null) {
                    s += trie.subSum();
                }
            }
        }
        return s;
    }

    public static void main(String[] args) {
        MapSum mapSum = new MapSum();
        mapSum.insert("apple", 3);
        System.out.println(mapSum.sum("ap"));
    }
}
