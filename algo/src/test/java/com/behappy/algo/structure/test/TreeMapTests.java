package com.behappy.algo.structure.test;

import com.behappy.algo.structure.TreeMap;
import com.behappy.algo.structure.test.common.JavaMapTest;
import com.behappy.algo.structure.test.common.MapTest;
import com.behappy.algo.structure.test.common.Utils;
import com.behappy.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TreeMapTests {

    @Test
    public void testTreeMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "TreeMap";
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        java.util.Map<String, Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                data.unsorted, data.sorted, data.invalid));
    }
}
