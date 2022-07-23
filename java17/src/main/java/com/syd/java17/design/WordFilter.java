package com.syd.java17.design;

import com.syd.java17.struct.Solution;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

class WordFilter {
    static class Trie {
        Map<Character, Trie> map;
        TreeSet<Integer> pq = new TreeSet<>(Comparator.reverseOrder());
        void insert(String s, int idx) {
            char[] cs = s.toCharArray();
            Trie t = this;
            for (char c : cs) {
                if (t.map == null) {
                    t.map = new HashMap<>();
                }
                t.pq.add(idx);
                t = t.map.computeIfAbsent(c, k -> new Trie());
            }
            t.pq.add(idx);
        }

        TreeSet<Integer> getIndices(String prefix) {
            TreeSet<Integer> set = new TreeSet<>(Comparator.reverseOrder());
            char[] cs = prefix.toCharArray();
            Trie t = this;
            for (char c : cs) {
                if (t.map == null) {
                    return null;
                }
                Trie child = t.map.get(c);
                if (child == null) {
                    return null;
                }
                t = child;
            }
            return t.pq;
        }
    }
    Trie prefix = new Trie();
    Trie suffix = new Trie();

    public WordFilter(String[] words) {
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            prefix.insert(s, i);
            suffix.insert(new StringBuilder(s).reverse().toString(), i);
        }
    }
    
    public int f(String pref, String suff) {
        TreeSet<Integer> preSet = prefix.getIndices(pref);
        TreeSet<Integer> sufSet = suffix.getIndices(new StringBuilder(suff).reverse().toString());
        if (preSet == null || sufSet == null) {
            return -1;
        }
        Iterator<Integer> preIt = preSet.iterator(), sufIt = sufSet.iterator();
        if (preIt.hasNext() && sufIt.hasNext()) {
            int preIdx = preIt.next(), sufIdx = sufIt.next();
            do {
                if (preIdx < sufIdx) {
                    if (sufIt.hasNext()) {
                        sufIdx = sufIt.next();
                    } else {
                        break;
                    }
                } else if (preIdx > sufIdx) {
                    if (preIt.hasNext()) {
                        preIdx = preIt.next();
                    } else {
                        break;
                    }
                } else {
                    return preIdx;
                }
            } while (true);
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(Solution.invokeResults(
                WordFilter.class,
                "[\"WordFilter\",\"f\"]",
                "[[[\"apple\"]],[\"a\",\"e\"]]"
        )));
        System.out.println(Arrays.toString(Solution.invokeResults(
                WordFilter.class,
                "[\"WordFilter\",\"f\"]",
                "[[[\"abbba\",\"abba\"]],[\"ab\",\"ba\"]]"
        )));
        URL url = WordFilter.class.getClassLoader().getResource("./in.txt");
        FileInputStream fis = new FileInputStream(url.getPath());
        Scanner sc = new Scanner(fis);
        System.out.println(Arrays.toString(Solution.invokeResults(
                WordFilter.class,
                sc.nextLine(),
                sc.nextLine()
        )));
    }
}

