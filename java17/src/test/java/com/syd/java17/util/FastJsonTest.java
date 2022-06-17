package com.syd.java17.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * @author songyide
 * @date 2022/6/17
 */
public class FastJsonTest {
    @Test
    void test() {
        JSONObject jsonObject = JSON.parseObject("{\"a\":1,\"b\":2}");
        String s = jsonObject.toJSONString();
    }
}
