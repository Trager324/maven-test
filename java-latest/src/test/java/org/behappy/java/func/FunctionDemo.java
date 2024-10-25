package org.behappy.java.func;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

public class FunctionDemo {
    static final List<List<Integer>> MAT = List.of(
            List.of(2, 2, 2, 2, 2),
            List.of(1, 2, 3, 4, 5),
            Arrays.asList(3, null, 3, 3, 3),
            Arrays.asList(3, 3, 3, 3, 3)
    );

    static <T> Consumer<T> println() {
        return System.out::println;
    }

    static int compareIntList(List<Integer> a1, List<Integer> a2) {
        return Integer.compare(a1.get(0), a2.get(0));
    }

    @Test
    void func0() {
        MAT.stream().sorted((a1, a2) -> a1.get(0) - a2.get(0)).forEach(println());
        MAT.stream().sorted((a1, a2) -> Integer.compare(a1.get(0), a2.get(0))).forEach(println());
        MAT.stream().sorted(Comparator.comparingInt(a -> a.get(0))).forEach(println());
        MAT.stream().sorted(FunctionDemo::compareIntList).forEach(println());
    }

    @Test
    void func1() {
        // Static method reference
        Predicate<String> f1 = String::isBlank;
        // Bound method ref
        Predicate<String> f2 = ""::equals;
        Predicate<String> f3 = Predicate.isEqual("");
        var f = f2.or(f1).and(f3).negate();
        System.out.println(f.test("1"));

        // Unbound method ref
        Consumer<Integer> con = Integer::notify;
        // Class constructor. Supplier always used as generator
        Supplier<List<Integer>> sl = ArrayList::new;
        // Array constructor can be seen as a class constructor with an int parameter
        // like: T[](int n) { this.length = n; }
        // Thus this method ref can represent as Function<Integer, T[]>
        Function<Integer, int[]> fa = int[]::new;
        IntFunction<int[]> fa2 = int[]::new;

        // Same as BiFunction<T,T,T>. Widely used as merger in reducing action
        BinaryOperator<Integer> bo = Integer::sum;
        IntStream.rangeClosed(1, 100).boxed().reduce(bo).ifPresent(System.out::println);
        var toMap = toMap(Integer::bitCount, Function.identity(), bo);

        // Same as Function<T, T>. Hardly used
        UnaryOperator<String> uo = String::toLowerCase;
    }

    @SuppressWarnings("removal")
    @Test
    void stream1() {
        // How to create stream

        // Arrays.stream / Stream.of
        var s1 = Arrays.stream(new int[]{1, 2, 3}).boxed();
        var s2 = IntStream.of(5, 6, 7).boxed();

        // Collection.stream() / StreamSupport.stream()
        var s3 = MAT.get(0).stream();
        var s4 = StreamSupport.stream(MAT.get(1).spliterator(), false);

        // Build stream: Stream.builder().build() / Stream.concat
        var s5 = Stream.<Integer>builder().add(1).add(2).build();
        var s6 = Stream.concat(s1, s2);
        // Concat var streams
        var s7 = Stream.of(s1, s2, s3).flatMap(Function.identity());

        // Infinite stream since jdk 9
        var si = Stream.iterate(0, x -> true, x -> x + 1);
        // Infinite stream at jdk 8
        var sg = Stream.generate(new Supplier<Integer>() {
            int x = 0;

            @Override
            public Integer get() {
                return x++;
            }
        });
        try (si) {
            // Don't use "forEach" action without any limit on infinite stream
            var t1 = Thread.ofPlatform().start(() ->
                    System.out.println(si.limit(100).map(i -> -i).reduce(Integer::sum)));
            TimeUnit.SECONDS.sleep(3);
            // Thread t1 is now on infinite loop. Interrupt is useless
            // That is to say, infinite stream must be cautious treated
            t1.stop();
            // And reuse this stream will raise exception because this inconsistency has occurred
            System.out.printf("Infinite stream si is now on: %d%n", si.findFirst().orElseThrow());
        } catch (InterruptedException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Test
    void stream2() {
        IntStream.range(0, MAT.size()).boxed().map(MAT::get).forEach(println());
        IntStream.range(0, MAT.size()).boxed().flatMap(i -> MAT.get(i).stream()).forEach(println());
        MAT.stream().filter(p -> p.size() == 5).forEach(println());
        // "{a: {b: 1}}"
        JSONObject jo = new JSONObject();
        var ab1 = Optional.of(jo)
                .map(i -> i.getJSONObject("a"))
                .map(i -> i.getInteger("b"))
                .orElse(null);
    }

    @Test
    void streamInAction() {
        try (var is = getClass().getClassLoader().getResourceAsStream("xzq.json")) {
            var data = JSON.parseArray(Objects.requireNonNull(is));

            //            System.out.printf("%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s%n",
            //                    "id", "name", "一级分类id", "一级分类name", "二级分类id", "二级分类name", "是否显示"
            //            );
            //            for (int i = 0; i < data.size(); i++) {
            //                var row = data.getJSONObject(i);
            //                System.out.printf("%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s%n",
            //                        row.getString("id"),
            //                        row.getString("name"),
            //                        row.getString("category1_id"),
            //                        row.getString("category1_name"),
            //                        row.getString("category2_id"),
            //                        row.getString("category2_name"),
            //                        row.getString("is_hidden")
            //                );
            //            }

            var map = data.stream().map(p -> (JSONObject) p)
                    .collect(toMap(p -> p.getString("id"), p -> p));
            var map2 = data.stream().map(p -> (JSONObject) p)
                    .collect(HashMap::new, (m, v) -> m.put("id", null), HashMap::putAll);
            var tree = data.stream().map(p -> (JSONObject) p)
                    .collect(groupingBy(p -> p.getString("category1_id"),
                            groupingBy(p -> p.getString("category2_id"),
                                    mapping(p -> p.getString("id"), toList()))));
            System.out.println(JSON.toJSONString(tree));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void cmp1() {
        MAT.stream().sorted(Comparator
                        .<List<Integer>, Integer>comparing(a -> a.get(0))
                        .thenComparing(a -> a.get(1), Comparator.nullsFirst(Comparator.naturalOrder())))
                .forEach(println());

        Map.of(1, 1).entrySet().stream().sorted(Map.Entry.comparingByKey());
        //        MAT.stream().sorted(Comparator.comparing(a -> a.get(1))).forEach(println());
    }

    @Test
    void mapCompute() {
        Map<String, BigDecimal> cacheMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            String key = "%.1f".formatted(Math.random());
            System.out.println(cacheMap.computeIfAbsent(key, BigDecimal::new));
        }
        Map<Integer, List<String>> map = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            var s = String.valueOf(i);
            map.computeIfAbsent(s.length(), k -> new ArrayList<>()).add(s);
        }
    }

    @Test
    void nonInterfering() {
        List<String> l = new ArrayList<>(Arrays.asList("one", "two"));
        Stream<String> sl = l.stream();
        l.add("three");
        String s = sl.collect(joining(" "));
        System.out.println(s);
    }
}
