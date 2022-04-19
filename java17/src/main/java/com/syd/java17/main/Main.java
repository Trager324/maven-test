package com.syd.java17.main;


import java.util.*;

public class Main {
    static int[][] matrixMultiply(int[][] ma, int[][] mb, int n, int m) {
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    res[i][j] += ma[i][k] * mb[k][j];
                }
                res[i][j] %= m;
            }
        }
        return res;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            int n = input.nextInt(), k = input.nextInt(), m = input.nextInt();
            int[][] mat = new int[n][n], t = new int[n][n], s = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    mat[i][j] = input.nextInt() % m;
                    s[i][j] = mat[i][j];
                    t[i][j] = mat[i][j];
                }
            }
            for (int i = 1; i < k; i++) {
                t = matrixMultiply(mat, t, n, m);
                for (int j = 0; j < n; j++) {
                    for (int l = 0; l < n; l++) {
                        s[j][l] = (s[j][j] + t[j][l]) % m;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                System.out.print(s[i][0]);
                for (int j = 1; j < n; j++) {
                    System.out.print(" " + s[i][j]);
                }
                System.out.println();
            }
        }
    }
}

class A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        next:
        while (input.hasNext()) {
            int m = input.nextInt(), k = input.nextInt(), sum = 0;
            int[] values = new int[m];
            for (int i = 0; i < m; i++) {
                values[i] = input.nextInt();
                sum += values[i];
            }
            if (sum % k != 0) {
                System.out.println("False");
                continue;
            }
            int x = sum / k;
            Arrays.sort(values);
            if (values[m - 1] > x) {
                System.out.println("False");
                continue;
            }
            int[] fri = new int[k];
            for (int i = m - 1; i >= 0; i--) {
                int value = values[i];
                int j;
                for (j = 0; j < k; j++) {
                    if (fri[j] + value <= x) {
                        fri[j] += value;
                        break;
                    }
                }
                if (j == k) {
                    System.out.println("False");
                    continue next;
                }
            }
            System.out.println("True");
        }
    }
}

class B {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            int m = input.nextInt(), n = input.nextInt(), k = input.nextInt(), s = 0, t = 0;
            while (k-- > 0) {

            }
        }
    }
}

class C {
//    static int cnt = 0;
    static int[] map = {2, 4, 3, 5, 1, 6, 0, 7, 8};
    static List<int[]> res = new ArrayList<>();
    static void dfs(boolean[] visited, int[] num, int index) {
        if (index == 9) {
            int a = num[map[0]] * 10 + num[map[1]], b = num[map[2]] * 10 + num[map[3]],
                    c = num[map[4]] * 10 + num[map[5]], d = num[map[6]] * 100 + num[map[7]] * 10 + num[map[8]];
            if (a * b == c * d) {
                res.add(new int[]{a,b,c,d});
//                cnt++;
//                System.out.printf("%d x %d = %d x %d\n", a, b, c, d);
//                System.out.printf("%d x %d = %d x %d = %d\n", a, b, c, d, a*b);
            }
            return;
        }
        for (int i = 1; i <= 9; i++) {
            if (!visited[i]) {
                num[index] = i;
                visited[i] = true;
                dfs(visited, num, index + 1);
                visited[i] = false;
            }
        }
    }
    public static void main(String[] args) {
        boolean[] visited = new boolean[10];
        int[] num = new int[9];
        for (int i = 1; i <= 9; i++) {
            num[0] = i;
            visited[i] = true;
            for (int j = 1; j <= 9; j++) {
                if (!visited[j] && i * j <= 9) {
                    num[1] = j;
                    visited[j] = true;
                    for (int k = j + 1; k < 9; k++) {
                        if (!visited[k]) {
                            num[2] = k;
                            visited[k] = true;
                            for (int l = k + 1; l <= 9; l++) {
                                if (!visited[l]) {
                                    num[3] = l;
                                    visited[l] = true;
                                    dfs(visited, num, 4);
                                    visited[l] = false;
                                }
                            }
                            visited[k] = false;
                        }
                    }
                    visited[j] = false;
                }
            }
            visited[i] = false;
        }
        res.sort((p, q) -> {
            if (p[0] == q[0]) {
                if (p[1] == q[1]) {
                    if (p[2] == q[2]) {
                        return Integer.compare(p[3], q[3]);
                    }
                    return p[2] - q[2];
                }
                return p[1] - q[1];
            }
            return p[0] - q[0];
        });
        for (int[] re : res) {
//            System.out.printf("%d x %d = %d x %d = %d\n", re[0], re[1], re[2], re[3], re[0] * re[1]);
            System.out.printf("%d x %d = %d x %d\n", re[0], re[1], re[2], re[3]);
        }
//        System.out.println(cnt);
    }
}

class D {
    public static void main(String[] args) {

    }
}
