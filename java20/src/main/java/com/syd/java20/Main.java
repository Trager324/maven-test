package com.syd.java20;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author syd
 */
@ComponentScan
public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(Main.class);
        var factory = ac.getBean(IntFactory.class);
        System.out.println(factory.get("a"));
        System.out.println(factory.get("b"));
        System.out.println(factory.get("gg"));

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