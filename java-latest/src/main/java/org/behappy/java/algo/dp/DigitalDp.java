package org.behappy.java.algo.dp;

import java.util.Arrays;

/**
 * 数位dp模板
 *
 * @author songyide
 * @date 2022/10/18
 */
public class DigitalDp {

    private char[] digits;
    private char[] s;
    private int[] dp;

    public int atMostNGivenDigitSet(String[] digits, int n) {
        this.digits = String.join("", digits).toCharArray();
        s = Integer.toString(n).toCharArray();
        dp = new int[s.length];
        Arrays.fill(dp, -1);
        return f(0, true, false);
    }

    private int f(int i, boolean isLimit, boolean isNum) {
        // 如果填了数字，则为 1 种合法方案
        if (i == s.length) return isNum ? 1 : 0;
        // 在不受到任何约束的情况下，返回记录的结果，避免重复运算
        if (!isLimit && isNum && dp[i] >= 0) return dp[i];
        var res = 0;
        // 前面不填数字，那么可以跳过当前数位，也不填数字
        if (!isNum)
            // isLimit 改为 false，因为没有填数字，位数都比 n 要短，自然不会受到 n 的约束
            // isNum 仍然为 false，因为没有填任何数字
            res = f(i + 1, false, false);
        // 根据是否受到约束，决定可以填的数字的上限
        var up = isLimit ? s[i] : '9';
        // 注意：对于一般的题目而言，如果此时 isNum 为 false，则必须从 1 开始枚举，由于本题 digits 没有 0，所以无需处理这种情况
        // 枚举要填入的数字 d
        for (var d : digits) {
            // d 超过上限，由于 digits 是有序的，后面的 d 都会超过上限，故退出循环
            if (d > up) break;
            // isLimit：如果当前受到 n 的约束，且填的数字等于上限，那么后面仍然会受到 n 的约束
            // isNum 为 true，因为填了数字
            res += f(i + 1, isLimit && d == up, true);
        }
        // 在不受到任何约束的情况下，记录结果
        if (!isLimit && isNum) dp[i] = res;
        return res;
    }
}
