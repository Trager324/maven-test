package com.syd.java19.incubator;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 实验性封装线段树
 *
 * @param <E> 线段树信息
 * @param <T> 标记
 * @author songyide
 * @date 2022/8/4
 */
public class SegmentTree<E, T extends SegmentTree.Tag> {
    public interface Tag {
        /**
         * 是否初始值
         *
         * @return 是否初始值
         */
        boolean isDefault();

        /**
         * 初始化
         */
        void init();
    }

    public static class Info {
        long maxv;

        Info() {}

        Info(long x) {
            this.maxv = x;
        }

        @Override
        public String toString() {
            return maxv + "";
        }
    }
    static class TagImpl implements Tag {
        long add;

        TagImpl() {init();}

        TagImpl(long add) {
            this.add = add;
        }

        /**
         * 是否初始值
         *
         * @return 是否初始值
         */
        public boolean isDefault() {return add == 0;}

        /**
         * 初始化
         */
        public void init() {this.add = 0;}
    }

    /**
     * 单点修改
     *
     * @param pos 修改位置
     * @param x   修改值
     */
    public void change(int pos, long x) {
        change(1, 1, size, pos, x);
    }

    /**
     * 区间修改
     *
     * @param ql 左端点
     * @param qr 右端点
     */
    public void modify(int ql, int qr, long val) {
        modify(1, 1, size, ql, qr, tagGenerator.apply(val));
    }

    /**
     * 区间查询
     *
     * @param ql 左端点
     * @param qr 右端点
     * @return 区间值
     */
    public E query(int ql, int qr) {
        return query(1, 1, size, ql, qr);
    }

    class Node {
        // 结点值
        E val;
        // 标记
        T t;

        Node() {
            this.val = infoSupplier.get();
            this.t = tagSupplier.get();
        }

        Node(long x) {
            this.val = infoGenerator.apply(x);
            this.t = tagGenerator.apply(x);
        }
    }

    /**
     * 值合并函数
     */
    final BinaryOperator<E> infoMerger;

    /**
     * 标记合并函数
     */
    final BinaryOperator<T> tagMerger;
    /**
     * 加标记函数
     */
    final BiFunction<E, T, E> infoFunc;
    /**
     * 默认值
     */
    final Supplier<E> infoSupplier;
    /**
     * 默认标记
     */
    final Supplier<T> tagSupplier;
    /**
     * 值生成函数
     */
    final Function<Long, E> infoGenerator;
    /**
     * 标记生成函数
     */
    final Function<Long, T> tagGenerator;
    final int size;
    final Node[] tree;

    /**
     * 初始化线段树
     *
     * @param a 初始化数组，下标从1开始
     */
    @SuppressWarnings("unchecked")
    public SegmentTree(long[] a,
                       BinaryOperator<E> infoMerger,
                       BinaryOperator<T> tagMerger,
                       BiFunction<E, T, E> infoFunc,
                       Supplier<E> infoSupplier,
                       Supplier<T> tagSupplier,
                       Function<Long, E> infoGenerator,
                       Function<Long, T> tagGenerator) {
        this.infoMerger = infoMerger;
        this.tagMerger = tagMerger;
        this.infoFunc = infoFunc;
        this.infoSupplier = infoSupplier;
        this.tagSupplier = tagSupplier;
        this.infoGenerator = infoGenerator;
        this.tagGenerator = tagGenerator;
        this.size = a.length - 1;
        this.tree = new SegmentTree.Node[size << 2];
        build(a, 1, 1, size);
    }

    public SegmentTree(int size,
                       BinaryOperator<E> infoMerger,
                       BinaryOperator<T> tagMerger,
                       BiFunction<E, T, E> infoFunc,
                       Supplier<E> infoSupplier,
                       Supplier<T> tagSupplier,
                       Function<Long, E> infoGenerator,
                       Function<Long, T> tagGenerator) {
        this(new long[size], infoMerger, tagMerger, infoFunc, infoSupplier, tagSupplier, infoGenerator, tagGenerator);
    }

    static int mid(int l, int r) {return (l + r) >> 1;}

    static int left(int id) {return id << 1;}

    static int right(int id) {return (id << 1) + 1;}

    final void update(int id) {
        // 更新结点
        tree[id].val = infoMerger.apply(tree[left(id)].val, tree[right(id)].val);
    }

    final void setTag(int id, T t) {
        tree[id].val = infoFunc.apply(tree[id].val, t);
        tree[id].t = tagMerger.apply(tree[id].t, t);
    }

    final void pushDown(int id) {
        if (!tree[id].t.isDefault()) {
            // 标记不为初始值时标记下传
            setTag(left(id), tree[id].t);
            setTag(right(id), tree[id].t);
            tree[id].t.init();
        }
    }

    final void change(int id, int l, int r, int pos, long val) {
        if (l == r) {
            tree[id] = new Node(val);
            return;
        }
        int m = mid(l, r);
        if (pos <= m) change(left(id), l, m, pos, val);
        else change(right(id), m + 1, r, pos, val);
        // ‼递归后更新结点
        update(id);
    }

    final void modify(int id, int l, int r, int ql, int qr, T t) {
        if (l == ql && r == qr) {
            setTag(id, t);
            return;
        }
        // ‼标记下传
        pushDown(id);
        int m = mid(l, r);
        if (qr <= m) modify(left(id), l, m, ql, qr, t);
        else if (ql > m) modify(right(id), m + 1, r, ql, qr, t);
        else {
            modify(left(id), l, m, ql, m, t);
            modify(right(id), m + 1, r, m + 1, qr, t);
        }
        // ‼递归后更新结点
        update(id);
    }

    final E query(int id, int l, int r, int ql, int qr) {
        if (l == ql && r == qr) {
            return tree[id].val;
        }
        // ‼标记下传
        pushDown(id);
        int m = mid(l, r);
        if (qr <= m) return query(left(id), l, m, ql, qr);
        if (ql > m) return query(right(id), m + 1, r, ql, qr);
        return infoMerger.apply(query(left(id), l, m, ql, m), query(right(id), m + 1, r, m + 1, qr));
    }

    final void build(long[] a, int id, int l, int r) {
        if (l == r) {
            tree[id] = new Node(a[l]);
            return;
        }
        int m = mid(l, r);
        build(a, left(id), l, m);
        build(a, right(id), m + 1, r);
        if (tree[id] == null) tree[id] = new Node();
        // ‼递归后更新结点
        update(id);
    }
}
