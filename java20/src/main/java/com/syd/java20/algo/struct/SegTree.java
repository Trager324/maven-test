package com.syd.java20.algo.struct;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * 线段树模板
 *
 * @author songyide
 * @date 2022/8/4
 */
public class SegTree {
    public static class Info {
        long maxv;

        Info(long x) {
            this.maxv = x;
        }
    }

    static class Tag {
        long add;

        Tag(long add) {
            this.add = add;
        }

        /**
         * 是否初始值
         *
         * @return 是否初始值
         */
        boolean isDefault() {return add == 0;}

        /**
         * 初始化
         */
        void init() {this.add = 0;}
    }

    /**
     * 标记合并函数
     */
    static final BinaryOperator<Tag> TAG_MERGER = (t1, t2) -> new Tag(t1.add + t2.add);
    /**
     * 值合并函数
     */
    static final BinaryOperator<Info> INFO_MERGER = (v1, v2) -> new Info(Math.max(v1.maxv, v2.maxv));
    /**
     * 加标记函数
     */
    static final BiFunction<Info, Tag, Info> INFO_FUNC = (v, t) -> new Info(v.maxv + t.add);

    public SegTree(long[] a) {
        this.size = a.length - 1;
        this.tree = new Node[size << 2];
        build(a, 1, 1, size);
    }

    public SegTree(int size) {
        this(new long[size]);
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
        modify(1, 1, size, ql, qr, new Tag(val));
    }

    /**
     * 区间查询
     *
     * @param ql 左端点
     * @param qr 右端点
     * @return 区间值
     */
    Info query(int ql, int qr) {
        return query(1, 1, size, ql, qr);
    }

    static class Node {
        // 结点值
        Info val;
        // 标记
        Tag t;

        Node(long x) {
            this.val = new Info(x);
            this.t = new Tag(x);
        }
    }

    private final int size;
    private final Node[] tree;

    static int mid(int l, int r) {return (l + r) >> 1;}

    static int left(int id) {return id << 1;}

    static int right(int id) {return (id << 1) + 1;}

    final void update(int id) {
        // 更新结点
        tree[id].val = INFO_MERGER.apply(tree[left(id)].val, tree[right(id)].val);
    }

    final void setTag(int id, Tag t) {
        tree[id].val = INFO_FUNC.apply(tree[id].val, t);
        tree[id].t = TAG_MERGER.apply(tree[id].t, t);
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

    final void modify(int id, int l, int r, int ql, int qr, Tag t) {
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

    final Info query(int id, int l, int r, int ql, int qr) {
        if (l == ql && r == qr) {
            return tree[id].val;
        }
        // ‼标记下传
        pushDown(id);
        int m = mid(l, r);
        if (qr <= m) return query(left(id), l, m, ql, qr);
        if (ql > m) return query(right(id), m + 1, r, ql, qr);
        return INFO_MERGER.apply(query(left(id), l, m, ql, m), query(right(id), m + 1, r, m + 1, qr));
    }

    final void build(long[] a, int id, int l, int r) {
        if (l == r) {
            tree[id] = new Node(a[l]);
            return;
        }
        int m = mid(l, r);
        build(a, left(id), l, m);
        build(a, right(id), m + 1, r);
        if (tree[id] == null) tree[id] = new Node(0);
        // ‼递归后更新结点
        update(id);
    }
}
