package com.syd.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.function.Function;

/**
 * 高精度运算工具类
 *
 * @author songyide
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Arith {
    public static final BigDecimal _100 = BigDecimal.valueOf(100);

    /**
     * 统计接口默认运算精度
     */
    public static final int SCALE_STATS_DEF = 6;
    /**
     * 比率型统计精度范围
     */
    public static final int SCALE_RATE_MAX = 4;
    public static final int SCALE_RATE_MIN = 2;
    /**
     * 高精度比较器，null值在前
     */
    public static final Comparator<BigDecimal> CMP_DECIMAL = (p, q) -> {
        if (p == null) {
            return -1;
        } else if (q == null) {
            return 1;
        }
        return p.compareTo(q);
    };

    public static final Function<BigDecimal, String> FMT_STATS_DEF = d -> {
        if (d == null) {
            return "/";
        }
        // 超过精度进行四舍五入
        if (d.scale() > Arith.SCALE_STATS_DEF) {
            d = d.setScale(Arith.SCALE_STATS_DEF, RoundingMode.HALF_UP);
        }
        return d.toPlainString();
    };

    public static final Function<BigDecimal, String> FMT_RATE_DEF = d -> {
        if (d == null) {
            return "/";
        }
        d = Arith._100.multiply(d);
        if (d.scale() > SCALE_RATE_MAX) {
            d = d.setScale(SCALE_RATE_MAX, RoundingMode.HALF_UP);
        } else if (d.scale() < SCALE_RATE_MIN) {
            // 精度不足则补0
            d = d.setScale(SCALE_RATE_MIN, RoundingMode.HALF_UP);
        }
        return d.toPlainString() + "%";
    };

    public static BigDecimal numberToBigDecimal(Number num) {
        // 排除可能导致精度丢失的类型
        if (num instanceof Long) {
            return BigDecimal.valueOf((Long)num);
        } else if (num instanceof BigDecimal) {
            return (BigDecimal)num;
        } else if (num instanceof BigInteger) {
            return new BigDecimal((BigInteger)num);
        }
        // 其他转换为double即可
        return num == null ? BigDecimal.ZERO : BigDecimal.valueOf(num.doubleValue());
    }

    /**
     * 取两个数的doubleValue计算比率，保留六位，四舍五入
     *
     * @date 2022/5/11
     */
    public static BigDecimal rate(Number n1, Number n2) {
        return rate(numberToBigDecimal(n1), numberToBigDecimal(n2));
    }

    /**
     * 计算BigDecimal比率，保留六位，四舍五入
     *
     * @date 2022/5/11
     */
    public static BigDecimal rate(BigDecimal d1, BigDecimal d2) {
        return rate(d1, d2, SCALE_STATS_DEF, RoundingMode.HALF_UP);
    }

    /**
     * 计算BigDecimal比率，四舍五入
     *
     * @date 2022/5/11
     */
    public static BigDecimal rate(BigDecimal d1, BigDecimal d2, int scale) {
        return rate(d1, d2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 计算BigDecimal比率
     *
     * @date 2022/5/11
     */
    public static BigDecimal rate(BigDecimal d1, BigDecimal d2, int scale, RoundingMode rm) {
        BigDecimal val = div(d1, d2, scale + 2, rm);
        return val == null ? null : val.subtract(BigDecimal.ONE);
    }

    /**
     * 取两个数的doubleValue进行除法，保留六位，四舍五入
     *
     * @date 2022/5/11
     */
    public static BigDecimal div(Number n1, Number n2) {
        return div(numberToBigDecimal(n1), numberToBigDecimal(n2), SCALE_STATS_DEF, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal除法，保留六位，四舍五入
     *
     * @date 2022/5/11
     */
    public static BigDecimal div(BigDecimal d1, BigDecimal d2) {
        return div(d1, d2, SCALE_STATS_DEF, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal除法，四舍五入
     *
     * @date 2022/5/11
     */
    public static BigDecimal div(BigDecimal d1, BigDecimal d2, int scale) {
        return div(d1, d2, scale, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal除法
     *
     * @date 2022/5/11
     */
    public static BigDecimal div(BigDecimal d1, BigDecimal d2, int scale, RoundingMode rm) {
        if (d1 == null || d2 == null || BigDecimal.ZERO.compareTo(d2) == 0) {
            return null;
        }
        return d1.divide(d2, scale, rm);
    }

    /**
     * 单位转换，单位转换率为空表示不需要转换
     *
     * @param source 值
     * @param rate   转换率
     * @return 积
     */
    public static BigDecimal exchange(BigDecimal source, BigDecimal rate) {
        return rate == null ? source : source == null ? null : source.multiply(rate);
    }

    /**
     * BigDecimal乘法
     *
     * @param d1 乘数1
     * @param d2 乘数2
     * @return 积
     */
    public static BigDecimal mul(BigDecimal d1, BigDecimal d2) {
        return d1 != null && d2 != null ? d1.multiply(d2) : null;
    }

    /**
     * BigDecimal加法
     *
     * @param d1 加数1
     * @param d2 加数2
     * @return 积
     */
    public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
        return d1 == null ? d2 : d2 == null ? d1 : d1.add(d2);
    }
}
