package com.syd.java.lib;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


public class FastJsonBooleanTest {
    @Test
    void test() {
        System.out.println(JSON.toJSONString(new A()));
    }

    static class A {
        public Boolean isSuccess() {
            fail();
            return null;
        }
    }
}
