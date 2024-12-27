package org.behappy.java.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Getter
public class ExampleBean {
    @Value("${my.custom.property}")
    private String customProperty;
}