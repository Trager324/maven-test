package com.syd.java20.struct;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author asus
 */

public class MyHeap<E> extends AbstractQueue<E> {
    private final ArrayList<E> queue;
    private final Comparator<? super E> comparator;

    public MyHeap(Collection<? extends E> c, Comparator<? super E> comparator) {
        queue = new ArrayList<>();
        queue.addAll(c);
        this.comparator = comparator;
        heapify();
    }

    public static void main(String[] args) {
        MyHeap<Integer> heap = new MyHeap<>(List.of(9, 8, 7, 6, 5, 4, 3, 2, 1), Integer::compare);
        assert heap.judge();
        heap = new MyHeap<>(List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0), Integer::compare);
        assert heap.judge();
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            heap.offer(random.nextInt());
            System.out.println(heap);
            assert heap.judge();
        }
        for (int i = 0; i < 100; i++) {
            heap.poll();
            assert heap.judge();
        }
        System.out.println(heap);
//        System.out.println(heap);
    }

    void heapify() {
        int size = size();
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            percolateDown(i);
        }
        System.out.println(queue);
    }

    void percolateDown(int index) {
        int size = size(), half = size >> 1;
        E e = queue.get(index);
        while (index < half) {
            int li = (index << 1) + 1, ri = li + 1;
            E tmp = queue.get(li);
            if (ri < size && comparator.compare(tmp, queue.get(ri)) > 0) {
                tmp = queue.get(li = ri);
            }
            if (comparator.compare(e, tmp) <= 0) {
                break;
            }
            queue.set(index, tmp);
            index = li;
        }
        queue.set(index, e);
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean offer(E o) {
        int k = size();
        queue.add(o);
        while (k > 0) {
            int parent = (k - 1) >> 1;
            E e = queue.get(parent);
            if (comparator.compare(o, e) >= 0) break;
            queue.set(k, e);
            k = parent;
        }
        queue.set(k, o);
        return true;
    }

    @Override
    public E poll() {
        int size = size() - 1;
        E o = peek();
        queue.set(0, queue.get(size));
        queue.remove(size);
        percolateDown(0);
        return o;
    }

    @Override
    public E peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return queue.get(0);
    }

    private boolean judge() {
        for (int i = 1; i < size(); i++) {
            if (comparator.compare(queue.get(i), queue.get((i - 1) >> 1)) < 0) {
                return false;
            }
        }
        return true;
    }
}

class IntHeap {
    private final Comparator<Integer> comparator;
    private int[] queue;
    private int size;

    public IntHeap(int[] arr) {
        this(arr, (p, q) -> Integer.compare(q, p));
    }

    public IntHeap(int[] arr, Comparator<Integer> comparator) {
        queue = arr;
        size = arr.length;
        this.comparator = comparator;
        heapify();
    }

    public static void main(String[] args) {
        IntHeap heap = new IntHeap(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
        assert heap.judge();
        heap = new IntHeap(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        System.out.println(heap);
        assert heap.judge();
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            heap.poll();
            assert heap.judge();
        }
        for (int i = 0; i < 10; i++) {
            heap.offer(random.nextInt());
            assert heap.judge();
        }
        assert heap.judge();
        for (int i = 0; i < 10; i++) {
            heap.poll();
            assert heap.judge();
        }
        for (int i = 0; i < 10; i++) {
            heap.offer(random.nextInt());
            assert heap.judge();
        }
        System.out.println(heap);
//        System.out.println(heap);
    }

    public boolean offer(int o) {
        int k = size();
        queue[size++] = o;
        while (k > 0) {
            int parent = (k - 1) >> 1;
            int e = queue[parent];
            if (comparator.compare(o, e) >= 0) break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = o;
        return true;
    }

    void heapify() {
        int n = size();
        for (int i = (n >> 1) - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    void percolateDown(int index) {
        int n = size(), half = n >> 1;
        int e = queue[index];
        while (index < half) {
            int li = (index << 1) + 1, ri = li + 1;
            int tmp = queue[li];
            if (ri < n && comparator.compare(tmp, queue[ri]) > 0) {
                tmp = queue[li = ri];
            }
            if (comparator.compare(e, tmp) <= 0) {
                break;
            }
            queue[index] = tmp;
            index = li;
        }
        queue[index] = e;
    }

    public int size() {
        return size;
    }

    public int poll() {
        int o = peek();
        queue[0] = queue[--size];
        percolateDown(0);
        return o;
    }

    @Override
    public String toString() {
        return Arrays.toString(queue);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return queue[0];
    }

    private boolean judge() {
        for (int i = 1; i < size(); i++) {
            if (comparator.compare(queue[i], queue[(i - 1) >> 1]) < 0) {
                return false;
            }
        }
        return true;
    }
}
