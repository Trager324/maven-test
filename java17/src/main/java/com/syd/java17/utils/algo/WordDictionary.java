package com.syd.java17.utils.algo;

class WordDictionary {
    private static final int NODE_NUM = 26;
    private final TrieNode root = new TrieNode();
    static class TrieNode {
        TrieNode[] nodes;
        boolean isWord;
        TrieNode() {
            nodes = new TrieNode[NODE_NUM];
        }
    }

    public void addWord(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            int val = c - 'a';
            if (cur.nodes[val] == null) {
                cur.nodes[val] = new TrieNode();
            }
            cur = cur.nodes[val];
        }

    }

    public boolean search(char[] word, int idx, TrieNode t) {
        if (word.length == idx) {
            return t.isWord;
        }
        if (word[idx] == '.') {
            for (TrieNode node : t.nodes) {
                if (node != null && search(word, idx + 1, node)) {
                    return true;
                }
            }
            return false;
        } else {
            int val = word[idx] - 'a';
            return t.nodes[val] != null && search(word, idx + 1, t.nodes[val]);
        }
    }
    public boolean search(String word) {
        return search(word.toCharArray(), 0, root);
    }
}
