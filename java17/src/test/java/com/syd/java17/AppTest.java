package com.syd.java17;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author SYD
 * @description Spring测试类
 * @date 2022/4/20
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class AppTest {

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
