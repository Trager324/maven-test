package com.behappy.java.nullsafety.packageinfo;

import org.springframework.lang.Nullable;

@SuppressWarnings({"ResultOfMethodCallIgnored", "java:S2201", "unused", "SameParameterValue"})
public class TestPackage {
    static String fieldNonNull = "";
    @Nullable
    static String fieldNullable;

    static String funNonNull(String str) {
        return str;
    }

    @Nullable
    static String funNullable(@Nullable String str) {
        return str;
    }

    public static void main(String[] args) {
        fieldNonNull.length();
        fieldNullable.length();
        funNonNull(null).length();
        funNullable(null).length();
    }
}
