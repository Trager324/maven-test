package com.syd.java20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.retry.annotation.EnableRetry;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipInputStream;

/**
 * @author syd
 * @date 2022/3/29
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableRetry(proxyTargetClass = true)
public class TestApp {
    public static void main(String[] args) {    }

    //    @RestController
    //    static class A {
    //        @RequestMapping(value = "/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    //        public ResponseEntity<AbstractResource> func(HttpServletResponse rsp,
    //                                                     @PathVariable("fileName") String fileName) {
    //            String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
    //                    .replaceAll("\\+", "%20");
    //            rsp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + encoded);
    //            return ResponseEntity.ok(new InputStreamResource(Objects.requireNonNull(getClass().getClassLoader()
    //                    .getResourceAsStream(fileName))));
    //        }
    //    }
}
