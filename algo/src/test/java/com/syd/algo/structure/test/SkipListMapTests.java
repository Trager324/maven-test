package com.syd.algo.structure.test;

import com.syd.algo.structure.SkipListMap;
import com.syd.algo.structure.test.common.JavaMapTest;
import com.syd.algo.structure.test.common.MapTest;
import com.syd.algo.structure.test.common.Utils;
import com.syd.algo.structure.test.common.Utils.TestData;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SkipListMapTests {

    @Test
    public void testSkipListMap() {
        TestData data = Utils.generateTestData(1000);

        String mapName = "SkipListMap";
        SkipListMap<String, Integer> map = new SkipListMap<String, Integer>();
        java.util.Map<String, Integer> jMap = map.toMap();

        assertTrue(MapTest.testMap(map, String.class, mapName,
                data.unsorted, data.invalid));
        assertTrue(JavaMapTest.testJavaMap(jMap, Integer.class, mapName,
                data.unsorted, data.sorted, data.invalid));
    }

}
