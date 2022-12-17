package com.syd.common.util;

import com.syd.common.constant.ResponseCode;
import com.syd.common.exception.BaseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author songyide
 * @date 2022/5/12
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeUtils extends DateUtils {
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String CH_YYYY_M_D = "yyyy年M月d日";
    public static final String TIME_ZONE_DEF = "GMT+8";

    public static final DateTimeFormatter FMT_DEF = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
    public static final DateTimeFormatter FMT_DATE = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    public static final DateTimeFormatter FMT_YMD = DateTimeFormatter.ofPattern(YYYYMMDD);
    public static final DateTimeFormatter FMT_CH_YMD = DateTimeFormatter.ofPattern(CH_YYYY_M_D);

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.SSS", "yyyy.MM.dd HH:mm",
    };

    /**
     * 用各种格式尝试解析日期
     *
     * @throws BaseException 解析异常
     */
    public static LocalDateTime parse(String str) {
        LocalDateTime res = parseNullable(str);
        if (res == null) {
            throw BaseException.of(ResponseCode.A0400, "日期格式不正确 - " + str);
        }
        return res;
    }

    /**
     * 用各种格式尝试解析日期
     */
    public static LocalDateTime parseNullable(String str) {
        if (str == null || (str = str.trim()).length() == 0) {
            return null;
        }
        try {
            return toLocalDateTime(parseDate(str, PARSE_PATTERNS));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 尝试用给定格式解析
     *
     * @throws BaseException 解析异常
     */
    public static LocalDateTime parse(String str, DateTimeFormatter fmt) {
        LocalDateTime res = parseNullable(str, fmt);
        if (res == null) {
            throw BaseException.of(ResponseCode.A0400, "日期格式不正确 - " + str);
        }
        return res;
    }

    /**
     * 尝试用给定格式解析
     */
    public static LocalDateTime parseNullable(String str, DateTimeFormatter fmt) {
        if (str == null || (str = str.trim()).length() == 0) {
            return null;
        }
        try {
            TemporalAccessor ta = fmt.parse(str);
            return LocalDateTime.of(
                    secureGet(ta, ChronoField.YEAR),
                    secureGet(ta, ChronoField.MONTH_OF_YEAR),
                    secureGet(ta, ChronoField.DAY_OF_MONTH),
                    secureGet(ta, ChronoField.HOUR_OF_DAY),
                    secureGet(ta, ChronoField.MINUTE_OF_HOUR),
                    secureGet(ta, ChronoField.SECOND_OF_MINUTE),
                    secureGet(ta, ChronoField.NANO_OF_SECOND)
            );
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static String formatDate(TemporalAccessor time) {
        return format(time, FMT_DATE);
    }

    /**
     * 格式化日期
     */
    public static String format(LocalDateTime dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public static String format(TemporalAccessor time, DateTimeFormatter formatter) {
        return time == null ? null : formatter.format(time);
    }

    /**
     * Date转换为LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取去年的时间，用于计算同比
     */
    public static LocalDateTime getSameYearTime(LocalDateTime date) {
        return date == null ? null : date.minusYears(1);
    }

    /**
     * 计算一段时间对应的上期时间，用于计算环比
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 上期时间的开始和结束时间
     */
    public static LocalDateTime[] getLastTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return new LocalDateTime[]{null, null};
        }
        LocalDateTime tmp = end.minusDays(1);
        // 结束时间为每月第一天时归到上一月，计算月份差
        long diff = tmp.getYear() * 12L + tmp.getMonthValue() - start.getYear() * 12L - start.getMonthValue() + 1;
        return new LocalDateTime[]{start.minusMonths(diff), end.minusMonths(diff)};
    }

    /**
     * 计算一段时间对应的上期时间，用于计算环比
     *
     * @param start    开始时间
     * @param end      结束时间
     * @param startDef 自定义开始时间
     * @param endDef   自定义结束时间
     * @return 上期时间的开始和结束时间
     */
    public static LocalDateTime[] getLastTimeRange(LocalDateTime start, LocalDateTime end, LocalDateTime startDef, LocalDateTime endDef) {
        if (startDef != null && endDef != null) {
            return new LocalDateTime[]{startDef, endDef};
        }
        return getLastTimeRange(start, end);
    }

    private static int secureGet(TemporalAccessor ta, ChronoField cf) {
        return ta.isSupported(cf) ? ta.get(cf) : (int)cf.range().getMinimum();
    }

    /**
     * 获取当前年月日
     *
     * @return 获取当前时间 yyyyMMdd
     */
    public static String getCurrentDate() {
        return FMT_YMD.format(LocalDate.now());
    }

    public static LocalDateTime getDayEnd(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.withHour((int)ChronoField.HOUR_OF_DAY.range().getMaximum())
                .withMinute((int)ChronoField.MINUTE_OF_HOUR.range().getMaximum())
                .withSecond((int)ChronoField.SECOND_OF_MINUTE.range().getMaximum());
    }
}
