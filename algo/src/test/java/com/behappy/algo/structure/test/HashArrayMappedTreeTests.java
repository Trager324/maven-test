package com.behappy.algo.structure.test;

import com.behappy.algo.structure.HashArrayMappedTrie;
import com.behappy.algo.structure.test.common.JavaMapTest;
import com.behappy.algo.structure.test.common.MapTest;
import com.behappy.algo.structure.test.common.Utils;
import com.behappy.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HashArrayMappedTreeTests {

    @Test
    public void testHAMT() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "HAMT";
        HashArrayMappedTrie<Integer, String> map = new HashArrayMappedTrie<Integer, String>();
        java.util.Map<Integer, String> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, Integer.class, mapName,
                data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                data.unsorted, data.sorted, data.invalid));
    }
}
