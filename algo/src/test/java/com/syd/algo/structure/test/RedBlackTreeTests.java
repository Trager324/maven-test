package com.syd.algo.structure.test;

import com.syd.algo.structure.RedBlackTree;
import com.syd.algo.structure.test.common.JavaCollectionTest;
import com.syd.algo.structure.test.common.TreeTest;
import com.syd.algo.structure.test.common.Utils;
import com.syd.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class RedBlackTreeTests {

    @Test
    public void testRedBlackTree() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "Red-Black Tree";
        RedBlackTree<Integer> bst = new RedBlackTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Integer.class, bstName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Integer.class, bstName,
                data.unsorted, data.sorted, data.invalid));
    }
}
