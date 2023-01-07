package com.syd.algo.structure.test;

import com.syd.algo.structure.SkipList;
import com.syd.algo.structure.test.common.JavaCollectionTest;
import com.syd.algo.structure.test.common.SetTest;
import com.syd.algo.structure.test.common.Utils;
import com.syd.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class SkipListTests {

    @Test
    public void testSkipList() {
        TestData data = Utils.generateTestData(1000);

        String sName = "SkipList";
        SkipList<Integer> sList = new SkipList<Integer>();
        Collection<Integer> lCollection = sList.toCollection();

        assertTrue(SetTest.testSet(sList, sName,
                data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Integer.class, sName,
                data.unsorted, data.sorted, data.invalid));
    }
}
