package com.syd.java19.lib;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;

import static org.junit.jupiter.api.Assertions.fail;


public class FastJsonBooleanTest {
    static class A {
        public Boolean isSuccess() {
            fail();
            return null;
        }
    }

    @Test
    void test() {
        System.out.println(JSON.toJSONString(new A()));
    }
}
