package com.syd.java17;

import com.syd.java17.framework.TestData;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author syd Spring测试类
 * @date 2022/4/20
 */
public class BaseTest {
    protected Map<String, Method> methodMap = new HashMap<>();
    {
        try {
            String className = getClass().getName();
            if (className.endsWith("Test")) {
                className = className.substring(0, className.length() - 4);
            }
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods()) {
                methodMap.put(method.getName(), method);
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected void test(List<? extends TestData<?, ?>> testDataList) {
        for (TestData<?, ?> testData : testDataList) {
            String testMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            String methodName = testMethodName;
            if (testMethodName.startsWith("test")) {
                methodName = Character.toLowerCase(testMethodName.charAt(4)) + testMethodName.substring(5);
            } else if (testMethodName.endsWith("Test")) {
                methodName = testMethodName.substring(0, testMethodName.length() - 4);
            }
            try {
                Method method = methodMap.get(methodName);
                Object actual = method.invoke(testData.getTarget(), testData.getArgs());
                assertEquals(testData.getExpected(), actual);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
