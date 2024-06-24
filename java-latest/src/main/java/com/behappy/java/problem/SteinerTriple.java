package com.behappy.java.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 现有 k 个人，你可以举办任意多次由三个人参加的聚会，现要求任意两个人都同时参加聚会恰好一次，试构造一组聚会方案。 可以说明，在给定的范围内一定有解。 一行一个正整数 k(1≤k≤3000)，保证 kmod6 为 1 或 3。
 * <p><a href="https://www.cnblogs.com/lyc-music/p/15729925.html">出处</a></p>
 *
 * @author songyide
 * @date 2022/11/2
 */
public class SteinerTriple {

    int n;
    int[][] id;
    int[][] a;
    SteinerTriple(int n) {
        if (n % 6 != 3 && n % 6 != 1) {
            throw new IllegalArgumentException("No result for " + n);
        }
        this.n = n;
        this.id = new int[n][4];
        this.a = new int[n][n];
    }

    public static void main(String[] args) {
        try (var input = new Scanner(System.in)) {
            while (input.hasNext()) {
                int n = input.nextInt();
                var res = new SteinerTriple(n).solve();
                System.out.println(res);
                System.out.println(n * (n - 1) / 6 - res.size());
            }
        }
    }

    void init0(int nowX, int nowY, int i, int x) {
        while (a[nowX][nowY] == 0) {
            a[nowX][nowY] = i;
            nowX--;
            nowY++;
            if (nowX == 0) nowX = x;
            if (nowY == x + 1) nowY = 1;
        }
    }

    void init() {
        //构造可交换的幂等拟群
        int x = n / 3;
        for (int i = 1; i <= x; i++)
            for (int j = 1; j <= 3; j++)
                id[i][j] = (i - 1) * 3 + j;
        for (int i = 1; i <= x; i++) {
            init0(i, i, i, x);
        }
    }

    void init1() {
        //构造可交换的半幂等拟群
        int x = (n - 1) / 3;
        for (int i = 1; i <= x; i++)
            for (int j = 1; j <= 3; j++)
                id[i][j] = (i - 1) * 3 + j;
        for (int i = 1; i <= x / 2; i++) {
            init0(i, i, i, x);
        }
        for (int i = x / 2 + 1; i <= x; i++) {
            init0(i - x / 2 + 1, i - x / 2, i, x);
        }
    }

    List<List<Integer>> solve() {
        List<List<Integer>> res = new ArrayList<>(n * (n - 1) / 6);
        if (n % 6 == 3) {
            init();
            for (int i = 1; i <= n / 3; i++)
                for (int j = i; j <= n / 3; j++) {
                    if (i == j)
                        res.add(List.of(id[i][1], id[i][2], id[i][3]));
                    else {
                        for (int k = 1; k <= 3; k++)
                            res.add(List.of(id[i][k], id[j][k], id[a[i][j]][(k) % 3 + 1]));
                    }
                }
        } else {
            init1();
            for (int i = 1; i <= n / 3; i++) {
                for (int j = i; j <= n / 3; j++) {
                    if (i == j && i <= n / 6)
                        res.add(List.of(id[i][1], id[i][2], id[i][3]));
                    else if (i != j) {
                        for (int k = 1; k <= 3; k++)
                            res.add(List.of(id[i][k], id[j][k], id[a[i][j]][(k) % 3 + 1]));
                    }
                }
                if (i <= n / 6) {
                    for (int k = 1; k <= 3; k++)
                        res.add(List.of(n, id[i][k], id[n / 6 + i][(k - 1 + 3 - 1) % 3 + 1]));
                }
            }
        }
        return res;
    }
}
