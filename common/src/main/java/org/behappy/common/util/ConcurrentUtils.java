package org.behappy.common.util;

import org.behappy.common.constant.ResponseCode;
import org.behappy.common.exception.BaseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @author songyide
 * @date 2022/6/10
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcurrentUtils {
    private static final int THREAD_COUNT = 5;
    private static final int DEFAULT_QUEUE_SIZE = 1000;
    private static final ExecutorService SERVICE = ExecutorUtils.newBoundedExecutor(THREAD_COUNT, DEFAULT_QUEUE_SIZE,
            "ConcurrentUtilsPool");

    /**
     * 类同于{@link lombok.SneakyThrows}
     */
    public static <T> T sneakyCall(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw BaseException.of(ResponseCode.B0001, e);
        }
    }

    /**
     * 异步执行所有任务，最后同步返回结果
     */
    @SafeVarargs
    public static <T> List<T> execTasks(Callable<? extends T>... tasks) {
        return execTasks(Arrays.asList(tasks), ConcurrentUtils::sneakyCall);
    }

    /**
     * 异步执行所有任务，最后同步返回结果
     *
     * @param invokers 异步方法调用者列表
     * @param func     调用者需要调用的函数
     */
    public static <T, R> List<R> execTasks(List<? extends T> invokers, Function<? super T, ? extends R> func) {
        return execTasks(invokers, func, SERVICE);
    }

    /**
     * 异步执行所有任务，最后同步返回结果
     *
     * @param invokers 异步方法调用者列表
     * @param func     调用者需要调用的函数
     */
    public static <T, R> List<R> execTasks(List<? extends T> invokers, Function<? super T, ? extends R> func,
                                           ExecutorService pool) {
        int n = invokers.size();
        @SuppressWarnings("unchecked")
        R[] results = (R[])new Object[n];
        List<CompletableFuture<Void>> tasks = IntStream.range(0, n).mapToObj(i -> CompletableFuture.runAsync(() -> {
            try {
                results[i] = func.apply(invokers.get(i));
            } catch (BaseException e) {
                log.error("异步任务异常 - {}", e.getMessage(), e);
            }
        }, pool)).toList();
        tasks.forEach(CompletableFuture::join);
        return Arrays.asList(results);
    }

    /**
     * 异步执行所有任务，最后同步返回结果
     */
    public static void execActions(Runnable... tasks) {
        execTasks(Arrays.asList(tasks), t -> {
            t.run();
            return null;
        });
    }

    /**
     * 异步执行所有任务，最后同步返回
     *
     * @param invokers 异步方法调用者列表
     * @param func     调用者需要调用的函数
     */
    public static <T> void execActions(List<T> invokers, Consumer<? super T> func) {
        execTasks(invokers, t -> {
            func.accept(t);
            return null;
        });
    }
}
