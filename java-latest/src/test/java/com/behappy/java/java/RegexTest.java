package com.behappy.java.java;

import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegexTest {
    public static final String PREFIX = "collections/";
    static final Pattern PTN_PATH = Pattern.compile("collections/(.*?)/inscriptions\\.json");

    @Nullable
    static String getPath0(@Nullable String s) {
        if (s == null) return null;
        Matcher matcher = PTN_PATH.matcher(s);
        if (matcher.matches()) {
            // 匹配括号中的字符串
            return matcher.group(1);
        }
        return null;
    }

    @Nullable
    static String getPath1(@Nullable String s) {
        if (s == null) return null;
        if (s.startsWith(PREFIX) && s.endsWith("/inscriptions.json") &&
                s.length() >= "collections/".length() + "/inscriptions.json".length()) {
            return s.substring("collections/".length(), s.length() - "/inscriptions.json".length());
        }
        return null;
    }

    @Nullable
    static String getPath(@Nullable String s) {
        return getPath1(s);
    }

    @Test
    void getPathTest() {
        assertEquals("(.*?)", getPath("collections/(.*?)/inscriptions.json"));
        assertEquals("1111", getPath("collections/1111/inscriptions.json"));
        assertEquals(null, getPath("\"collections/1111/inscriptions.json\""));
        assertEquals(null, getPath("collections/inscriptions.json"));
        assertEquals("", getPath("collections//inscriptions.json"));
    }
}
