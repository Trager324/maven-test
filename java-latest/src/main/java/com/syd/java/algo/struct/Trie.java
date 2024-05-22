package com.syd.java.algo.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songyide
 * @date 2022/7/7
 */
public class Trie {
    Map<Character, Trie> children;
    boolean isLeaf;

    public void insert(String s) {
        char[] cs = s.toCharArray();
        Trie t = this;
        for (char c : cs) {
            if (t.children == null) {
                t.children = new HashMap<>();
            }
            t = t.children.computeIfAbsent(c, k -> new Trie());
        }
        t.isLeaf = true;
    }

    public boolean contains(String s) {
        char[] cs = s.toCharArray();
        Trie t = this;
        for (char c : cs) {
            if (t.children == null) {
                return false;
            }
            Trie child = t.children.get(c);
            if (child == null) {
                return false;
            }
            t = child;
        }
        return t.isLeaf;
    }

    public String longestPrefix(String s) {
        char[] cs = s.toCharArray();
        Trie t = this;
        int n = cs.length;
        for (int i = 0; i < n; i++) {
            if (t.children == null) {
                return t.isLeaf ? s.substring(0, i) : null;
            }
            Trie child = t.children.get(cs[i]);
            if (child == null) {
                return t.isLeaf ? s.substring(0, i) : null;
            }
            t = child;
        }
        return t.isLeaf ? s : null;
    }

    public String shortestPrefix(String s) {
        char[] cs = s.toCharArray();
        Trie t = this;
        int n = cs.length;
        for (int i = 0; i < n; i++) {
            if (t.children == null) {
                return t.isLeaf ? s.substring(0, i) : null;
            }
            Trie child = t.children.get(cs[i]);
            if (child == null) {
                return t.isLeaf ? s.substring(0, i) : null;
            }
            if (child.isLeaf) {
                return s.substring(0, i + 1);
            }
            t = child;
        }
        return t.isLeaf ? s : null;
    }
}
