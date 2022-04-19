package com.syd.java17;

import com.syd.java17.controller.AController;
import com.syd.java17.service.A;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author SYD
 * @description
 * @date 2022/3/29
 */
@SpringBootApplication
public class TestApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(TestApp.class);
        System.out.println(ac.getBean(AController.class));
    }
}
