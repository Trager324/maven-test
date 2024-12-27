package org.behappy.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author syd
 * @date 2022/3/29
 */
@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableRetry(proxyTargetClass = true)
public class TestApp {

    public static void main(String[] args) {
        var ac = SpringApplication.run(TestApp.class, args);
    }
}
