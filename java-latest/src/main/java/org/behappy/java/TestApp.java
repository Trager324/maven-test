package org.behappy.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.retry.annotation.EnableRetry;

import java.io.IOException;

/**
 * @author syd
 */
@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableRetry(proxyTargetClass = true)
@EnableConfigurationProperties
//@ComponentScan(basePackages = {"org.behappy.java", "org.behappy.common"})
public class TestApp {
    public static ConfigurableApplicationContext ac;

    public static void main(String[] args) throws IOException {
        ac = SpringApplication.run(TestApp.class, args);
    }
}
