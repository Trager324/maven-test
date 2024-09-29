package com.behappy.java.nullsafety.custom;

import com.behappy.java.nullsafety.annotation.Even;
import com.behappy.java.nullsafety.annotation.NonNull;

public class TestEven {
    @Even
    static int vi = 0;
    @NonNull
    static String str = "";
    public static void main(String[] args) {
        vi = 1;
        str = null;
    }
}
