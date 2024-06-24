package com.behappy.java.syntax.test;

import lombok.Setter;

import javax.annotation.concurrent.Immutable;

@Immutable
@Setter
public class ImmutableTest {
    int a;

    @Setter
    static class A {
        int a;
    }

}
