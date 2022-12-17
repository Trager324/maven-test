package com.syd.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author songyide
 * @date 2022/8/31
 */
class TimeUtilsTest {

    static final LocalDateTime[][] SAMPLES = {
            {of(2022, 7, 1), of(2022, 7, 15)},
            {of(2022, 7, 1), of(2022, 8, 1)},
            {of(2022, 7, 15), of(2022, 8, 15)},
            {of(2022, 7, 2), of(2022, 8, 29)},
            {of(2022, 3, 1), of(2022, 3, 31)},
            {of(2022, 1, 1), of(2022, 7, 28)},
            {of(2022, 1, 28), of(2022, 7, 14)},
    };
    static final LocalDateTime[][] ANSWERS = {
            {of(2022, 6, 1), of(2022, 6, 15)},
            {of(2022, 6, 1), of(2022, 7, 1)},
            {of(2022, 5, 15), of(2022, 6, 15)},
            {of(2022, 5, 2), of(2022, 6, 29)},
            {of(2022, 2, 1), of(2022, 2, 28)},
            {of(2021, 6, 1), of(2021, 12, 28)},
            {of(2021, 6, 28), of(2021, 12, 14)},
    };

    public static LocalDateTime of(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth).atStartOfDay();
    }

    @Test
    void format() {
        TimeUtils.format(LocalDateTime.now(), TimeUtils.FMT_DEF);
    }

    @Test
    void getSameYearTime() {
    }

    @Test
    void getLastTimeRange() {
        for (int i = 0; i < SAMPLES.length; i++) {
            LocalDateTime[] range = TimeUtils.getLastTimeRange(SAMPLES[i][0], SAMPLES[i][1]);
            try {
                assertEquals(ANSWERS[i][0], range[0]);
                assertEquals(ANSWERS[i][1], range[1]);
            } catch (AssertionError e) {
                System.out.println(i);
                throw e;
            }
        }
    }
}