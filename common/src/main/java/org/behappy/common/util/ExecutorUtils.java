package org.behappy.common.util;

import org.behappy.common.constant.ResponseCode;
import org.behappy.common.exception.BaseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author songyide
 * @date 2022/9/19
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExecutorUtils {
    public static final RejectedExecutionHandler DEFAULT_HANDLER = (r, e) -> {
        throw BaseException.of(ResponseCode.B0315)
                .appendDebugInfo(e.toString() + "线程池拒绝任务:" + r.toString());
    };

    public static ThreadFactory newThreadFactory(String factoryName) {
        return new ThreadFactory() {
            private final ThreadGroup group = new ThreadGroup(factoryName);
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private final String namePrefix = factoryName + "-thread-";

            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
                if (t.isDaemon()) {
                    t.setDaemon(false);
                }
                if (t.getPriority() != Thread.NORM_PRIORITY) {
                    t.setPriority(Thread.NORM_PRIORITY);
                }
                return t;
            }
        };
    }

    public static ThreadPoolExecutor newBoundedExecutor(int corePoolSize, int queueSize, String factoryName) {
        return newBoundedExecutor(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS,
                queueSize, factoryName);
    }

    public static ThreadPoolExecutor newBoundedExecutor(
            int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            int queueSize,
            String factoryName
    ) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                new ArrayBlockingQueue<>(queueSize), newThreadFactory(factoryName), DEFAULT_HANDLER);
    }
}
