package com.syd.java19;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author syd
 * @date 2022/3/29
 */
@SpringBootApplication
@EnableRetry
public class TestApp {
    @RestController
    static class A {
        @RequestMapping(value = "/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
        public ResponseEntity<AbstractResource> func(HttpServletResponse rsp,
                                                     @PathVariable String fileName) {
            String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            rsp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + encoded);
            return ResponseEntity.ok(new InputStreamResource(Objects.requireNonNull(getClass().getClassLoader()
                    .getResourceAsStream(fileName))));
        }
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication.run(TestApp.class);
    }
}
