package com.syd.java;


//import org.springframework.lang.NonNull;
//import org.springframework.lang.Nullable;

import lombok.Data;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Main {
    List<Integer> list;

    @Nullable
    static String funNullable(@Nullable String def) {
        return def;
    }

    @Nonnull
    static String funNonNull(@Nonnull String def) {
        return funNullable(def);
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()));
//        new BCryptPasswordEncoder().encode("pingyi001");
    }
}
