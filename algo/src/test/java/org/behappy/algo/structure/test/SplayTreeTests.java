package org.behappy.algo.structure.test;

import org.behappy.algo.structure.BinarySearchTree;
import org.behappy.algo.structure.SplayTree;
import org.behappy.algo.structure.test.common.JavaCollectionTest;
import org.behappy.algo.structure.test.common.TreeTest;
import org.behappy.algo.structure.test.common.Utils;
import org.behappy.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class SplayTreeTests {

    @Test
    public void testSplayTree() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "Splay Tree";
        BinarySearchTree<Integer> bst = new SplayTree<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Integer.class, bstName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Integer.class, bstName,
                data.unsorted, data.sorted, data.invalid));
    }

}
