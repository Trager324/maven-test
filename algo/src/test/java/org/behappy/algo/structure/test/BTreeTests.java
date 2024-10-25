package org.behappy.algo.structure.test;

import org.behappy.algo.structure.BTree;
import org.behappy.algo.structure.test.common.JavaCollectionTest;
import org.behappy.algo.structure.test.common.TreeTest;
import org.behappy.algo.structure.test.common.Utils;
import org.behappy.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class BTreeTests {

    @Test
    public void testBTree() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "B-Tree";
        BTree<Integer> bst = new BTree<Integer>(2);
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Integer.class, bstName, data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Integer.class, bstName,
                data.unsorted, data.sorted, data.invalid));
    }
}
