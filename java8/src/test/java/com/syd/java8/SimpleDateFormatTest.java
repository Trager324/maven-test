package com.syd.java8;

import lombok.var;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleDateFormatTest {
    static final int N = 2;
    static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final ExecutorService POOL = Executors.newFixedThreadPool(N);

    @Test
    void testFormat() {
        var date = new Date();
        var ans = SDF.format(date);
        System.out.println(ans);
        var futures = IntStream.range(0, N)
                .mapToObj(i -> (Callable<List<String>>)() -> IntStream.range(0, 100)
                        .mapToObj(j -> SDF.format(new Date(j * 1000L)))
                        .collect(Collectors.toList())
                ).map(POOL::submit)
                .collect(Collectors.toList());
        var data = futures.stream()
                .map(p -> {
                    try {
                        return p.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        data.forEach(list -> IntStream.range(0, list.size())
                        .forEach(i -> assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                        .format(new Date(i * 1000L)), list.get(i))));
    }

}
