package com.syd.java17;

import com.syd.java17.framework.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.service.ApiInfo;

import java.util.Map;

/**
 * @author syd Spring测试类
 * @date 2022/4/20
 */

@SpringBootTest
@AutoConfigureMockMvc
public class SpringTest extends BaseTest {
    @Autowired
    protected MockMvc mockMvc;
    protected String ctlPath;
    protected Map<String, ApiInfo> apiInfoMap;

    //    @Value(":${server.port}")
//    String port;
    @Value("${server.servlet.context-path:/}")
    String contextPath;

    public SpringTest() {
    }

}
