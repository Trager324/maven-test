package org.behappy.java17.util.struct;


import org.behappy.java17.util.algo.ArrayAlgo;

/**
 * @author songyide
 * @date 2022/8/4
 */
public class UnionSet {
    int[] fa, size;

    public UnionSet(int n) {
        fa = new int[n];
        size = new int[n];
        init();
    }

    public int find(int x) {
        // x 不是自身的父亲，即 x 不是该集合的代表
        if (x != fa[x]) {
            // 查找 x 的祖先直到找到代表，于是顺手路径压缩
            fa[x] = find(fa[x]);
        }
        return fa[x];
    }

    public void union(int x, int y) {
        // x 与 y 所在家族合并
        x = find(x);
        y = find(y);
        // 把 x 的祖先变成 y 的祖先的儿子
        fa[x] = y;
    }

    public void unionBySize(int x, int y) {
        int xx = find(x), yy = find(y);
        if (xx == yy) {
            return;
        }
        if (size[xx] > size[yy]) {
            // 保证小的合到大的里
            ArrayAlgo.swap(fa, xx, yy);
        }
        fa[xx] = yy;
        size[yy] += size[xx];
    }

    public void init() {
        for (int i = 0; i < fa.length; i++) {
            fa[i] = i;
        }
    }
}
