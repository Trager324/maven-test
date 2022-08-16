package com.syd.java17.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author syd Spring测试类
 * @date 2022/4/20
 */
public class BaseTest {
    protected Map<String, List<Method>> methodMap = new HashMap<>();

    public BaseTest() {
        try {
            String className = getClass().getName();
            if (className.endsWith("Test")) {
                className = className.substring(0, className.length() - 4);
            }
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods()) {
                method.setAccessible(true);
                methodMap.computeIfAbsent(method.getName(), k -> new ArrayList<>()).add(method);
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected void test(List<? extends TestData<?, ?>> testDataList) {
        String testMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String methodName = testMethodName;
        if (testMethodName.startsWith("test")) {
            methodName = Character.toLowerCase(testMethodName.charAt(4)) + testMethodName.substring(5);
        } else if (testMethodName.endsWith("Test")) {
            methodName = testMethodName.substring(0, testMethodName.length() - 4);
        }
        Method method = null;
        List<Method> list = methodMap.get(methodName);
        if (list.size() == 1) {
            method = list.get(0);
        } else {
            TestData<?, ?> data0 = testDataList.get(0);
            for (Method m : list) {
                Class<?>[] types = m.getParameterTypes();
                if (types.length == data0.args.length) {
                    boolean match = true;
                    for (int i = 0; i < types.length; i++) {
                        if (!types[i].isAssignableFrom(data0.args[i].getClass())) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        method = m;
                        break;
                    }
                }
            }
            if (method == null) {
                throw new RuntimeException("No method found for " + methodName + " with " + data0.args.length + " args");
            }
        }
        for (TestData<?, ?> testData : testDataList) {
            try {
                Object actual = method.invoke(testData.getTarget(), testData.getArgs());
                assertEquals(testData.getExpected(), actual);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
