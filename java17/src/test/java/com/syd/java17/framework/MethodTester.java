package com.syd.java17.framework;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author songyide
 * @date 2022/7/3
 */
public class MethodTester<R, T> {
    private final Method method;
    private final R target;

    public MethodTester(Method method) {
        this.method = method;
        this.target = null;
        this.method.setAccessible(true);
    }

    public MethodTester(Method method, R target) {
        this.method = method;
        this.target = target;
        this.method.setAccessible(true);
    }


    public void test(TestData<R, T> data) {
        try {
            R target = data.getTarget();
            if (target == null) {
                target = this.target;
            }
            T expected = data.getExpected();
            Class<?> expClazz = expected.getClass();
            if (expClazz.isArray()) {
                assertArrayEquals((Object[]) expected, (Object[]) method.invoke(target, data.getArgs()));
            } else {
                assertEquals(expected, method.invoke(target, data.getArgs()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testList(List<TestData<R, T>> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            TestData<R, T> data = dataList.get(i);
            try {
                test(data);
            } catch (AssertionError e) {
                System.out.println("第" + (i + 1) + "条测试失败");
                throw e;
            }
        }
    }
}
