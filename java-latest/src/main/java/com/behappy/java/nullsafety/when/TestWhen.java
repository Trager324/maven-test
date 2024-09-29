package com.behappy.java.nullsafety.when;



@SuppressWarnings({"ResultOfMethodCallIgnored", "java:S2201"})
public class TestWhen {
    interface UnknownFunction<T, U> {
        @javax.annotation.Nullable
        U apply(@javax.annotation.Nullable T t);
    }

    interface MaybeFunction<T, U> {
        @org.springframework.lang.Nullable
        U apply(@org.springframework.lang.Nullable T t);
    }

    public static void main(String[] args) {
        UnknownFunction<String, String> unknownFunction = s -> s;
        unknownFunction.apply(" ").length();
        MaybeFunction<String, String> maybeFunction = s -> s;
        maybeFunction.apply(" ").length();

    }
}
