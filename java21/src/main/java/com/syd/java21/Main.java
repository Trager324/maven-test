package com.syd.java21;

import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * @author syd
 */
@ComponentScan
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.printf("%x%n", 16);
        System.out.printf("%o%n", 16);
        System.out.printf("%d%n", 16);
        System.out.printf("%h%n", 16);
//        System.out.println(Instant.ofEpochMilli(Long.parseLong("11101001000000000010000000000011111010010", 2)));
//        System.out.println(new Date(0b11101001000000000010000000000011111010010L));
//        System.out.println(new Date(0b11101001000000000010000000000011111010010L));
//        System.out.println(Instant.ofEpochMilli(0b11101001000000000010000000000011111010010L));
//        System.out.println(Instant.EPOCH);

    }
}

@Configuration
class IntConfigure {
    @Bean
    public Integer ia() {return 1;}

    @Bean
    public Integer ib() {return 2;}
}

@Component
class IntFactory {
    private final Integer ia;
    private final Integer ib;

    public IntFactory(
            @Autowired @Qualifier("ia") Integer ia,
            @Autowired @Qualifier("ib") Integer ib) {
        this.ia = ia;
        this.ib = ib;
    }

    public Integer get(String s) {
        return switch (s) {
            case "a" -> ia;
            case "b" -> ib;
            default -> throw new RuntimeException();
        };
    }
}