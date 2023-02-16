package com.syd.java19;

import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.stream.Stream;

/**
 * @author syd
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(1 << 64);
        System.out.println(2 << 32);
        System.out.println(1 >> 64);
        System.out.println(2 >> 32);
        System.out.println(1 >>> 64);
        System.out.println(2 >>> 32);
        Stream.of();
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {
            scope.fork(() -> 1);
            scope.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
