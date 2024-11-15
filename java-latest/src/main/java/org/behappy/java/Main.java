package org.behappy.java;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.checkerframework.checker.units.qual.Length;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class Main {


    static void f1(Map<String, ? extends Map<String, Integer>> map) {

    }

    public static void main(String[] args) {
        var map = Map.of(
                "", new HashMap<String, Integer>());
        f1(map);
    }
}
