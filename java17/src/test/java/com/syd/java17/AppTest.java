package com.syd.java17;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author SYD
 * @description Spring测试类
 * @date 2022/4/20
 */

@SpringBootTest(classes = TestApp.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class AppTest {

    @BeforeEach
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @AfterEach
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
