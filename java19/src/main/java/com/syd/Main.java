package com.syd;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author songyide
 * @date 2022/9/20
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AtomicInteger ai = new AtomicInteger(0);
        var start = System.currentTimeMillis();
        var tasks = IntStream.range(0, 10000000)
                .mapToObj(i -> Thread.ofVirtual().unstarted(ai::incrementAndGet))
                .toList();
        tasks.forEach(Thread::start);
        for (var t : tasks) {
            t.join();
        }
        System.out.println(ai.get());
        System.out.println(System.currentTimeMillis() - start);
    }
}