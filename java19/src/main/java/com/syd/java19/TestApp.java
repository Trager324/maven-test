package com.syd.java19;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author syd
 * @date 2022/3/29
 */
@SpringBootApplication
public class TestApp {
    @ConfigurationProperties(prefix = "server")
    @Component
    @Data
    static class A {
        private int port;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(TestApp.class);
        var a = ac.getBean(A.class);
        System.out.println(a.getPort());
    }
}
