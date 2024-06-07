package com.syd.common.util;


import cn.hutool.core.collection.ConcurrentHashSet;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class Iterators2Test {

    static <T> void assertIterate(Iterator<T> expected, Iterator<T> actual) {
        while (expected.hasNext()) {
            assertEquals(expected.next(), actual.next());
        }
    }

    void joinThreadSet(Set<Thread> threads) throws InterruptedException {
        for (var t : threads) {
            t.join();
            threads.remove(t);
        }
    }

    void assertBufferBorder(Iterator<Integer> expected, Iterator<Integer> actual,
                            Set<Thread> threads, AtomicBoolean enable) throws InterruptedException {
        // 等待inserter填充缓冲区
        joinThreadSet(threads);
        // 断言缓冲区边界，不会触发阈值
        assertFalse(enable.get());

        // 加载几个数据
        assertIterate(Iterators.limit(expected, 10), Iterators.limit(actual, 10));
        // 等待inserter填充缓冲区
        joinThreadSet(threads);
        // 缓冲区边界，已经触发阈值
        assertTrue(enable.get());
        // 重新设置标识
        enable.set(false);
        // 剩下元素进行正确性测试
        assertIterate(expected, actual);
    }

    @Test
    void testBufferOnDemand() throws InterruptedException {
        var enable = new AtomicBoolean();
        var capacity = 100;
        var source = new AbstractIterator<Integer>() {
            int v = 0;

            @Override
            protected Integer computeNext() {
                var next = v++;
                if (next != 5 && next % capacity == 5) {
                    // 加载几个元素后触发阈值
                    enable.set(true);
                }
                return next;
            }
        };
        Set<Thread> threads = new ConcurrentHashSet<>();
        var executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),
                r -> {
                    var t = new Thread(r);
                    threads.add(t);
                    return t;
                });
        var buffered = Iterators2.buffer(source, capacity, executor);
        assertBufferBorder(IntStream.range(0, capacity).iterator(), buffered, threads, enable);
        // 第二遍测试
        assertBufferBorder(IntStream.range(capacity, capacity * 2).iterator(), buffered, threads, enable);
    }

    <T> Iterator<T> newPageIteratorFromList(List<T> list, Pageable firstPage) {
        return Iterators2.pageIterator(pageable -> new PageImpl<>(list.subList(
                        Math.min(list.size(), (int) pageable.getOffset()),
                        Math.min(list.size(), (int) pageable.getOffset() + pageable.getPageSize()))),
                firstPage);
    }

    @Test
    void testPageIteratorForEmpty() {
        var itor = Iterators2.pageIterator(pageable -> Page.empty());
        assertFalse(itor.hasNext());
        itor = Iterators2.pageIterator(pageable -> Page.empty());
        assertThrows(NoSuchElementException.class, itor::next);
    }

    @Test
    void testPageIterateResult() {
        var list = List.of(0, 1, 2, 3, 4);
        var itor = newPageIteratorFromList(list, PageRequest.of(0, 1));
        assertIterate(list.iterator(), itor);
        itor = newPageIteratorFromList(list, PageRequest.of(0, 2));
        assertIterate(list.iterator(), itor);
        itor = newPageIteratorFromList(list, PageRequest.of(0, 10));
        assertIterate(list.iterator(), itor);
        itor = newPageIteratorFromList(list, PageRequest.of(1, 2));
        assertIterate(List.of(2, 3, 4).iterator(), itor);
    }
}