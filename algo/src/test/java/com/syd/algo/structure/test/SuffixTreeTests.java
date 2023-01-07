package com.syd.algo.structure.test;

import com.syd.algo.structure.SuffixTree;
import com.syd.algo.structure.test.common.SuffixTreeTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SuffixTreeTests {

    @Test
    public void testSuffixTree() {
        String bookkeeper = "bookkeeper";
        SuffixTree<String> tree = new SuffixTree<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(tree, bookkeeper));
    }
}
