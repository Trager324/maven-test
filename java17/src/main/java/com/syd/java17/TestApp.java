package com.syd.java17;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.boot.starter.autoconfigure.SpringfoxConfigurationProperties;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author syd
 * @date 2022/3/29
 */
@SpringBootApplication(exclude = {
//        HttpHandlerAutoConfiguration.class,
//        ServletWebServerFactoryAutoConfiguration.class,
//        RSocketRequesterAutoConfiguration.class,
//        DispatcherServletAutoConfiguration.class
})
public class TestApp {
    public void setAa(@Value("${aa:1}") Integer aa) {
        System.out.println(aa);
    }

    @Value("${bb:2}")
    public void setBb(Integer bb) {
        System.out.println(bb);
    }

    public TestApp(@Value("${cc:3}") Integer cc) {
        System.out.println(cc);
    }

    static final int g = ThreadLocalRandom.current().nextInt();
    public static void main(String[] args) {
        System.out.println(new TestApp(1).g);
        ConfigurableApplicationContext ac = SpringApplication.run(TestApp.class);
    }
}
