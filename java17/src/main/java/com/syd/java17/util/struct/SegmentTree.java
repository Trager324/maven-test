package com.syd.java17.util.struct;

/**
 * @author songyide
 * @date 2022/8/4
 */
public class SegmentTree {
    int[] a, b, d;

    public SegmentTree(int[] arr) {
        this.a = arr;
        b = new int[arr.length << 2];
        d = new int[arr.length << 2];
        build(1, arr.length, 0);
    }


    public void build(int s, int t, int p) {
        // 对 [s,t] 区间建立线段树,当前根的编号为 p
        if (s == t) {
            d[p] = a[s];
            return;
        }
        int m = s + ((t - s) >> 1);
        // 移位运算符的优先级小于加减法，所以加上括号
        // 如果写成 (s + t) >> 1 可能会超出 int 范围
        int left = left(p), right = right(p);
        build(s, m, left);
        build(m + 1, t, right);
        // 递归对左右区间建树
        d[p] = d[left] + d[right];
    }

    int left(int p) {
        return p << 1;
    }

    int right(int p) {
        return (p << 1) + 1;
    }

    public void add(int l, int r, int c, int s, int t, int p) {
        // [l, r] 为修改区间, c 为被修改的元素的变化量, [s, t] 为当前节点包含的区间, p
        // 为当前节点的编号
        if (l <= s && t <= r) {
            d[p] += (t - s + 1) * c;
            b[p] += c;
            return;
        }
        // 当前区间为修改区间的子集时直接修改当前节点的值,然后打标记,结束修改
        int m = s + ((t - s) >> 1);
        int left = left(p), right = right(p);
        if (b[p] > 0 && s != t) {
            // 如果当前节点的懒标记非空,则更新当前节点两个子节点的值和懒标记值
            d[left] += b[p] * (m - s + 1);
            d[right] += b[p] * (t - m);
            // 将标记下传给子节点
            b[left] += b[p];
            b[right] += b[p];
            // 清空当前节点的标记
            b[p] = 0;
        }
        if (l <= m) {
            add(l, r, c, s, m, left);
        }
        if (r > m) {
            add(l, r, c, m + 1, t, right);
        }
        d[p] = d[left] + d[right];
    }

    public int getSum(int l, int r, int s, int t, int p) {
        // [l, r] 为查询区间, [s, t] 为当前节点包含的区间, p 为当前节点的编号
        if (l <= s && t <= r) {
            return d[p];
        }
        // 当前区间为询问区间的子集时直接返回当前区间的和
        int m = s + ((t - s) >> 1);
        int left = left(p), right = right(p);
        if (b[p] > 0) {
            // 如果当前节点的懒标记非空,则更新当前节点两个子节点的值和懒标记值
            d[left] += b[p] * (m - s + 1);
            d[right] += b[p] * (t - m);
            // 将标记下传给子节点
            b[left] += b[p];
            b[right] += b[p];
            // 清空当前节点的标记
            b[p] = 0;
        }
        int sum = 0;
        if (l <= m) {
            sum = getSum(l, r, s, m, left);
        }
        if (r > m) {
            sum += getSum(l, r, m + 1, t, right);
        }
        return sum;
    }



//    public void update(int l, int r, int c, int s, int t, int p) {
//        if (l <= s && t <= r) {
//            d[p] = (t - s + 1) * c;
//            b[p] = c;
//            return;
//        }
//        int m = s + ((t - s) >> 1);
//        int left = left(p), right = right(p);
//        // 额外数组储存是否修改值
//        if (v[p] > 0) {
//            d[left] = b[p] * (m - s + 1);
//            d[right] = b[p] * (t - m);
//            b[left] = b[right] = b[p];
//            v[left] = v[right] = 1;
//            v[p] = 0;
//        }
//        if (l <= m) {
//            update(l, r, c, s, m, left);
//        }
//        if (r > m) {
//            update(l, r, c, m + 1, t, right);
//        }
//        d[p] = d[left] + d[right];
//    }
//
//    public int getSum(int l, int r, int s, int t, int p) {
//        if (l <= s && t <= r) {
//            return d[p];
//        }
//        int m = s + ((t - s) >> 1);
//        int left = left(p), right = right(p);
//        if (v[p] > 0) {
//            d[left] = b[p] * (m - s + 1);
//            d[right] = b[p] * (t - m);
//            b[left] = b[right] = b[p];
//            v[left] = v[right] = 1;
//            v[p] = 0;
//        }
//        int sum = 0;
//        if (l <= m) {
//            sum = getSum(l, r, s, m, left);
//        }
//        if (r > m) {
//            sum += getSum(l, r, m + 1, t, right);
//        }
//        return sum;
//    }
}
