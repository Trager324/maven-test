package com.syd.algo.structure.test;

import com.syd.algo.structure.HashMap;
import com.syd.algo.structure.test.common.JavaMapTest;
import com.syd.algo.structure.test.common.MapTest;
import com.syd.algo.structure.test.common.Utils;
import com.syd.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HashMapTests {

    @Test
    public void testHashMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "ProbingHashMap";
        HashMap<Integer, String> map = new HashMap<Integer, String>(HashMap.Type.PROBING);
        java.util.Map<Integer, String> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, Integer.class, mapName, data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName, data.unsorted, data.sorted, data.invalid));

        mapName = "LinkingHashMap";
        map = new HashMap<Integer, String>(HashMap.Type.CHAINING);
        jMap = map.toMap();

        assertTrue(MapTest.testMap(map, Integer.class, mapName, data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName, data.unsorted, data.sorted, data.invalid));
    }
}
