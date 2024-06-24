package com.behappy.java.bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
public class StringComparisonBenchmark {

    private static final String[] STRINGS = {
            "STRING_ONE", "STRING_TWO", "STRING_THREE", "STRING_FOUR", "STRING_FIVE",
            "STRING_SIX", "STRING_SEVEN", "STRING_EIGHT", "STRING_NINE", "STRING_TEN"
    };

    @Param({"STRING_ONE", "STRING_FIVE", "STRING_TEN", "NOT_PRESENT", "STRING_TWENTY"})
    private String input;

    @Benchmark
    public boolean testForLoopEquals() {
        for (String s : STRINGS) {
            if (s.equals(input)) {
                return true;
            }
        }
        return false;
    }

    @Benchmark
    public boolean testIfElse() {
        if ("STRING_ONE".equals(input)) return true;
        else if ("STRING_TWO".equals(input)) return true;
        else if ("STRING_THREE".equals(input)) return true;
        else if ("STRING_FOUR".equals(input)) return true;
        else if ("STRING_FIVE".equals(input)) return true;
        else if ("STRING_SIX".equals(input)) return true;
        else if ("STRING_SEVEN".equals(input)) return true;
        else if ("STRING_EIGHT".equals(input)) return true;
        else if ("STRING_NINE".equals(input)) return true;
        else if ("STRING_TEN".equals(input)) return true;
        else return false;
    }

    @Benchmark
    public boolean testSwitchStatement() {
        return switch (input) {
            case "STRING_ONE", "STRING_TWO", "STRING_THREE", "STRING_FOUR", "STRING_FIVE",
                 "STRING_SIX", "STRING_SEVEN", "STRING_EIGHT", "STRING_NINE", "STRING_TEN" -> true;
            default -> false;
        };
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringComparisonBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}