package org.behappy.algo.structure.test;

import org.behappy.algo.structure.TrieMap;
import org.behappy.algo.structure.test.common.JavaMapTest;
import org.behappy.algo.structure.test.common.MapTest;
import org.behappy.algo.structure.test.common.Utils;
import org.behappy.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TrieMapTests {

    @Test
    public void testTrieMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "TrieMap";
        TrieMap<String, Integer> map = new TrieMap<String, Integer>();
        java.util.Map<String, Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, String.class, mapName,
                data.unsorted, data.sorted, data.invalid));
    }
}
