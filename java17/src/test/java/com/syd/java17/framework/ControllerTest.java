package com.syd.java17.framework;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author songyide
 * @date 2022/8/4
 */
@SpringBootTest
@Slf4j
public class ControllerTest {
    public static final String LOCAL_URL = "http://localhost";

    @Autowired
    private RequestMappingHandlerMapping rmhm;
    @Autowired
    private WebApplicationContext context;
    @Value("${server.servlet.context-path:/}")
    private String servletPath;
    @Value(":${server.port}")
    private String serverPort;


    @Test
    void test() {
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : handlerMethods.entrySet()) {
            Map<String, String> map = new HashMap<>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            //获取当前方法所在类名
            Class<?> bean = method.getBeanType();
            //使用反射获取当前类注解内容
//            Api api = bean.getAnnotation(Api.class);
            RequestMapping requestMapping = bean.getAnnotation(RequestMapping.class);
            String[] value = requestMapping.value();
            map.put("parent",value[0]);
            //获取方法上注解以及注解值
//            ApiOperation methodAnnotation = method.getMethodAnnotation(ApiOperation.class);
//            String privilegeName = methodAnnotation.notes();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map.put("url", url);
            }
            map.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
            map.put("method", method.getMethod().getName()); // 方法名
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map.put("type", requestMethod.toString());
            }

            list.add(map);
        }

        System.out.println(list);
    }
}
