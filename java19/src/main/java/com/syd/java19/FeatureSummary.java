package com.syd.java19;

import java.io.FileNotFoundException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;

/**
 * @author songyide
 * @date 2022/6/17
 */
public class FeatureSummary {
    static final Random RANDOM = new Random();


    /**
     * @since 9
     */
    void _9() throws FileNotFoundException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
                .authenticator(Authenticator.getDefault()).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://foo.com/"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("file.json"))).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);
    }


    /**
     * @since 10
     */
    void _10() {
        var a = "1";
        System.out.println(a.getClass());
    }


    /**
     * @since 13
     */
    void _13() {
        String text = """
                SELECT * FROM users
                WHERE id = ?
                """;
        System.out.println(text);
    }


    /**
     * @since 14
     */
    void _14() {
        Object o = String.valueOf(RANDOM.nextBoolean() ? String.valueOf(RANDOM.nextInt(5)) : 1);
        if (o instanceof String s) {
            System.out.println(switch (s) {
                case "1" -> 1;
                case "2" -> 2;
                default -> {
                    System.out.println("default");
                    yield 0;
                }
            });
        }
    }


    /**
     * @since 16
     */
    record P(int x, int y) {}


    /**
     * @since 17
     */
    sealed interface B permits C {}

    static non-sealed private class C implements B {}

    void _17() {
        Object o = new Object[]{"1", null, new P(0, 0)}[RANDOM.nextInt(3)];
        switch (o) {
            case null -> System.out.println("null");
            case String s -> System.out.println(s);
            default -> System.out.println();
        }
    }

    record Circle(P c, int r) {}

    /**
     * @since 19
     */
    void _19() {
        Object o = new Object[]{"2", new Circle(new P(0, 0), 1),}[RANDOM.nextInt(2)];
        int res = switch (o) {
            case Circle(P ignored,var r)when r <= 0 -> -1;
            case Circle(P ignored,int r) -> r;
            default -> 0;
        };
        Thread.ofVirtual().start(() -> System.out.println(res));
    }

    public static void main(String[] args) throws Exception {
        FeatureSummary summary = new FeatureSummary();
        summary._9();
        summary._10();
        summary._13();
        summary._14();
        summary._17();
        summary._19();
    }
}
