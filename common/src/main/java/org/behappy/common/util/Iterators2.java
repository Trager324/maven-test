package org.behappy.common.util;

import com.google.common.collect.AbstractIterator;
import org.behappy.common.util.function.CheckedFunction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 迭代器工具类，补充guava中缺失的方法，主要使用装饰器进行包装
 *
 * @see Iterator
 * @see com.google.common.collect.Iterators
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Iterators2 {
    /**
     * 缓冲迭代器
     *
     * @param source   source
     * @param capacity capacity
     * @see <a href="https://stackoverflow.com/questions/2149244/bufferediterator-implementation">
     * bufferediterator-implementation</a>
     */
    public static <E> Iterator<E> buffer(final Iterator<E> source,
                                         int capacity) {
        return buffer(source, capacity, DEFAULT_EXECUTOR);
    }

    public static <E> Iterator<E> buffer(final Iterator<E> source,
                                         int capacity,
                                         final ExecutorService exec) {
        if (capacity <= 0) {
            return source;
        }
        final BlockingQueue<BufferItem<E>> queue = new ArrayBlockingQueue<>(capacity);

        // Temporary storage for an element we fetched but could not fit in the queue
        final AtomicReference<BufferItem<E>> overflow = new AtomicReference<>();
        final Runnable inserter = new Runnable() {
            public void run() {
                var state = IterateState.OK;
                E next = null;
                BufferItem<E> item;
                try {
                    if (source.hasNext()) {
                        next = source.next();
                    } else {
                        state = IterateState.END;
                    }
                    item = new BufferItem<>(next, state, null);
                } catch (Throwable t) {
                    state = IterateState.FAILED;
                    item = new BufferItem<>(null, state, t);
                }
                if (queue.offer(item)) {
                    // Keep buffering elements as long as we can
                    if (state != IterateState.END) {
                        exec.submit(this);
                    }
                } else {
                    // Save the element.  This also signals to the
                    // iterator that the inserter thread is blocked.
                    overflow.lazySet(item);
                }
            }
        };
        // Fetch the first element.
        // The inserter will resubmit itself as necessary to fetch more elements.
        exec.submit(inserter);

        return new AbstractIterator<>() {
            protected E computeNext() {
                try {
                    var item = queue.take();
                    var overflowElem = overflow.getAndSet(null);
                    if (overflowElem != null) {
                        // There is now a space in the queue
                        queue.put(overflowElem);
                        // Awaken the inserter thread
                        exec.submit(inserter);
                    }
                    return switch (item.state) {
                        case OK -> item.element;
                        case END -> endOfData();
                        case FAILED -> throw new RuntimeException("Exception in buffered iterator", item.throwable);
                    };
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return endOfData();
                }
            }
        };
    }

    private static final ExecutorService DEFAULT_EXECUTOR = Executors.newCachedThreadPool();

    public enum IterateState {
        OK, FAILED, END;
    }

    private record BufferItem<E>(@Nullable E element, IterateState state, @Nullable Throwable throwable) {}


    public static <E> Iterator<E> pageIterator(CheckedFunction<Pageable, Page<E>> func) {
        return pageIterator(func, Pageable.ofSize(1000));
    }

    public static <E> Iterator<E> pageIterator(CheckedFunction<Pageable, Page<E>> func, Pageable firstPage) {
        return new Iterator<>() {
            Iterator<E> currentIterator = Collections.emptyIterator();
            Pageable pageable = firstPage;

            @Override
            public boolean hasNext() {
                if (!currentIterator.hasNext()) {
                    if (this.currentIterator != Collections.emptyIterator()) {
                        this.pageable = this.pageable.next();
                    }
                    this.currentIterator = func.apply(this.pageable).iterator();
                    return this.currentIterator.hasNext();
                }
                return true;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return this.currentIterator.next();
            }
        };
    }
}
