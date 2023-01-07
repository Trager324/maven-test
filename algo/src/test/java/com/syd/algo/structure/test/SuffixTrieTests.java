package com.syd.algo.structure.test;

import com.syd.algo.structure.SuffixTrie;
import com.syd.algo.structure.test.common.SuffixTreeTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SuffixTrieTests {

    @Test
    public void testSuffixTrie() {
        String bookkeeper = "bookkeeper";
        SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
    }
}
