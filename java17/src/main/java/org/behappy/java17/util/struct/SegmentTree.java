package org.behappy.java17.util.struct;

import java.util.function.BiFunction;

/**
 * 线段树 - 基于数组的表示实现
 *
 * @author 01
 * @date 2021-01-27
 */
public class SegmentTree<E> {
    /**
     * 保存原始数组，即须要被构形成线段树的区间
     */
    private final E[] data;
    /**
     * 线段树的数组表示
     */
    private final E[] tree;

    /**
     * 用户自定义的区间合并逻辑
     */
    private final BiFunction<E, E, E> merger;

    @SuppressWarnings("unchecked")
    public SegmentTree(E[] arr, BiFunction<E, E, E> merger) {
        this.merger = merger;
        this.data = (E[])new Object[arr.length];
        System.arraycopy(arr, 0, this.data, 0, arr.length);

        // 开辟 4n 的数组空间用于构建线段树
        this.tree = (E[])new Object[arr.length << 2];
        // 构建线段树，传入根节点索引，以及区间的左右端点
        build(0, 0, data.length - 1);
    }

    /**
     * 查询区间[queryLeft, queryRight]的值，如[2, 5]
     */
    public E query(int queryLeft, int queryRight) {
        if (queryLeft < 0 || queryLeft >= data.length ||
                queryRight < 0 || queryRight >= data.length ||
                queryLeft > queryRight) {
            throw new IllegalArgumentException("Index is illegal");
        }

        return query(0, 0,
                data.length - 1, queryLeft, queryRight);
    }

    /**
     * 在以idx为根的线段树中[l...r]的范围里，搜索区间[ql...qr]的值
     */
    private E query(int idx, int l, int r, int ql, int qr) {
        // 找到了目标区间
        if (l == ql && r == qr) {
            return tree[idx];
        }

        int li = leftChild(idx), ri = rightChild(idx);
        // 计算中间点，须要避免整型溢出
        int mid = l + (r - l) / 2;

        if (ql >= mid + 1) {
            // 目标区间不在左子树中，查找右子树
            return query(ri, mid + 1, r, ql, qr);
        } else if (qr <= mid) {
            // 目标区间不在右子树中，查找左子树
            return query(li, l, mid, ql, qr);
        }

        // 目标区间一部分在右子树中，一部分在左子树中，则两个子树都须要找
        E lr = query(li, l, mid, ql, mid);
        E rr = query(ri, mid + 1, r, mid + 1, qr);

        // 找到目标区间的值，将其合并后返回
        return merger.apply(lr, rr);
    }

    /**
     * 将index位置的值，更新为e
     */
    public void set(int index, E e) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal");
        }
        data[index] = e;
        set(0, 0, data.length - 1, index, e);
    }

    /**
     * 在以idx为根的线段树中更新index的值为e
     */
    private void set(int idx, int left, int right, int index, E e) {
        // 找到了叶子节点
        if (left == right) {
            // 进行更新
            tree[idx] = e;
            return;
        }
        int mid = left + ((right - left) >> 1);
        // 将线段树数组划分为[left...mid]和[mid+1...right]两个区间
        int leftTreeIndex = leftChild(idx);
        int rightTreeIndex = rightChild(idx);
        if (index >= mid + 1) {
            // index在右子树
            set(rightTreeIndex, mid + 1, right, index, e);
        } else {
            // index在左子树
            set(leftTreeIndex, left, mid, index, e);
        }

        tree[idx] = merger.apply(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public int size() {
        return data.length;
    }

    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index is illegal");
        }
        return data[index];
    }

    /**
     * 返回彻底二叉树的数组表示中，一个索引所表示的元素的左子节点的索引
     */
    private int leftChild(int index) {
        return (index << 1) + 1;
    }

    /**
     * 返回彻底二叉树的数组表示中，一个索引所表示的元素的右子节点的索引
     */
    private int rightChild(int index) {
        return (index << 1) + 2;
    }

    /**
     * 在idx的位置建立表示区间[left...right]的线段树
     */
    private void build(int idx, int left, int right) {
        // 区间中只有一个元素，表明递归到底了
        if (left == right) {
            tree[idx] = data[left];
            return;
        }

        int leftTreeIndex = leftChild(idx);
        int rightTreeIndex = rightChild(idx);
        // 计算中间点，须要避免整型溢出
        int mid = left + ((right - left) >> 1);
        // 构建左子树
        build(leftTreeIndex, left, mid);
        // 构建右子树
        build(rightTreeIndex, mid + 1, right);

        // 对于两个区间的合并规则是与业务相关的，因此要调用用户自定义的逻辑来完成
        tree[idx] = merger.apply(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    /**
     * 遍历打印树中节点中值信息。
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null) {
                res.append(tree[i]);
            } else {
                res.append("null");
            }

            if (i != tree.length - 1) {
                res.append(", ");
            }
        }
        res.append(']');
        return res.toString();
    }
}