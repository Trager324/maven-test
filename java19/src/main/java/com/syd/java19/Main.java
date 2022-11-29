package com.syd.java19;

import com.syd.java19.incubator.SegmentTree;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * @author syd
 */
public class Main {
    static class Info {
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
    static class TagImpl implements SegmentTree.Tag {
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
     * 标记合并函数
     */
    static final BinaryOperator<TagImpl> TAG_MERGER = (t1, t2) -> new TagImpl(t1.add + t2.add);
    /**
     * 值合并函数
     */
    static final BinaryOperator<Info> INFO_MERGER = (v1, v2) -> new Info(Math.max(v1.maxv, v2.maxv));
    /**
     * 加标记函数
     */
    static final BiFunction<Info, TagImpl, Info> INFO_FUNC = (v, t) -> new Info(v.maxv + t.add);



    public static void main(String[] args) {
        try (var input = new Scanner(System.in)) {
            int n = input.nextInt(), q = input.nextInt();
            long[] a = new long[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = input.nextInt();
            }
            SegmentTree<Info, TagImpl> tree = new SegmentTree<>(a, INFO_MERGER, TAG_MERGER, INFO_FUNC,
                    Info::new, TagImpl::new, Info::new, TagImpl::new);
            while (q-- > 0) {
                int t = input.nextInt(), l = input.nextInt(), r = input.nextInt();
                if (t == 1) {
                    int d = input.nextInt();
                    tree.modify(l, r, d);
                } else {
                    System.out.println(tree.query(l, r).maxv);
                }
            }
        }
    }
}
