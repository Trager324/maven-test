package com.behappy.java;


public class Main {
    void f1(Object obj) {
        if (obj == this) {
            return;
        }
        if (!(obj instanceof Main other)) {
            return;
        }
        System.out.println(other);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.f1(m);
    }
}
