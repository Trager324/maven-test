import com.alibaba.fastjson2.JSON;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class ParallelTest {
    @Data
    static class Response<T> {
        T data;

        Response(T data) {
            this.data = data;
        }
    }

    @Data
    static class A {
        // 没有属性不会异常
        int a;
    }

    @Test
    void testB() {
        for (int i = 0; i < 100000; i++) {
            System.out.println(JSON.toJSONString(new Response<>(new A())));
        }
    }

    @Test
    void testList() {
        for (int i = 0; i < 100000; i++) {
            // Collections.singletonList(0)改成0也会异常，异常概率小一些
            System.out.println(JSON.toJSONString(new Response<>(Collections.singletonList(0))));
        }
    }
}

class ContrastTest {
    @Test
    void testParallel() {
        // 在ForkJoinPool中异常
        Stream.<Runnable>of(() -> {
                    for (int i = 0; i < 100000; i++) {
                        System.out.println(JSON.toJSONString(new ParallelTest.Response<>(Collections.singletonList(0))));
                    }
                },
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        System.out.println(JSON.toJSONString(new ParallelTest.Response<>(new ParallelTest.A())));
                    }
                }).parallel().forEach(Runnable::run);
    }

    @Test
    void normal() throws InterruptedException {
        // 普通线程池中执行正常
        ExecutorService pool = Executors.newFixedThreadPool(2);
        CountDownLatch cdl = new CountDownLatch(2);
        pool.execute(() -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println(JSON.toJSONString(new ParallelTest.Response<>(Collections.singletonList(0))));
            }
            cdl.countDown();
        });
        pool.execute(() -> {
            for (int i = 0; i < 100000; i++) {
                System.out.println(JSON.toJSONString(new ParallelTest.Response<>(new ParallelTest.A())));
            }
            cdl.countDown();
        });
        cdl.await();
        pool.shutdown();
    }
}
