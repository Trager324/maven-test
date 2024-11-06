package org.behappy.java;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.checkerframework.checker.units.qual.Length;
import org.springframework.stereotype.Component;

public class Main {

    static final String B = "B" + 1;

    @Pattern(regexp = B)
    void f1(Object obj) {
        switch (obj.toString()) {
            case B -> System.out.println("B");
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.f1(m);
    }
}
