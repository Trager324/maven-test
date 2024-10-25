package org.behappy.algo.structure.test;

import org.behappy.algo.structure.BinarySearchTree;
import org.behappy.algo.structure.Treap;
import org.behappy.algo.structure.test.common.JavaCollectionTest;
import org.behappy.algo.structure.test.common.TreeTest;
import org.behappy.algo.structure.test.common.Utils;
import org.behappy.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class TreapTests {

    @Test
    public void testTreap() {
        TestData data = Utils.generateTestData(1000);

        String bstName = "Treap";
        BinarySearchTree<Integer> bst = new Treap<Integer>();
        Collection<Integer> bstCollection = bst.toCollection();

        assertTrue(TreeTest.testTree(bst, Integer.class, bstName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(bstCollection, Integer.class, bstName,
                data.unsorted, data.sorted, data.invalid));
    }
}
