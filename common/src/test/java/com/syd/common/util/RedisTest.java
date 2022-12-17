package com.syd.common.util;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RedisTest {
    private final RedisService redisService;

    @Test
    public void test() {
        redisService.set("test", "test");
        String test = redisService.get("test");
        assertEquals("test", test);
        redisService.set("test", "test2", 1);
        System.out.println((String)redisService.get("test"));
    }
}
