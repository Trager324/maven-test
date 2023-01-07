package com.syd.canal.config;

import com.syd.canal.client.CanalClient;
import com.syd.canal.client.SimpleCanalClient;
import com.syd.canal.client.transfer.MessageTransponders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author jigua
 * @date 2018/3/19
 */
@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CanalClientConfiguration {
    private final CanalConfig canalConfig;

    @Bean
    CanalClient canalClient() {
        if (!canalConfig.isEnabled()) {
            return null;
        }
        CanalClient canalClient = new SimpleCanalClient(canalConfig, MessageTransponders.defaultMessageTransponder());
        canalClient.start();
        log.info("Starting canal client....");
        return canalClient;
    }
}

