package com.syd.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.retry.annotation.EnableRetry;

import java.util.Objects;

/**
 * @author syd
 * @date 2022/3/29
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableRetry(proxyTargetClass = true)
public class TestApp {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    void f() {
        var redisAtomicLong = new RedisAtomicLong("key", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
    }

    public static void main(String[] args) {
    }

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
