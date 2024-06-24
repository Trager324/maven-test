package org.behappy.canal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jigua
 * @date 2018/3/16
 */
@ConfigurationProperties(prefix = CanalConfig.PREFIX)
@Configuration
@Data
public class CanalConfig {
    public static final String PREFIX = "canal.client";
    public static final String DEFAULT_INSTANCE = "example";

    /**
     * 是否开启canal客户端
     */
    private boolean enabled = true;

    /**
     * instance config
     */
    @NestedConfigurationProperty
    private Map<String, Instance> instances = new LinkedHashMap<>();

    /**
     * instance config class
     * <p>对应canal的destination
     */
    @Data
    public static class Instance {
        /**
         * is cluster-mod
         */
        private boolean clusterEnabled;
        /**
         * zookeeper address
         */
        private Set<String> zookeeperAddress = new LinkedHashSet<>();
        /**
         * canal server host
         */
        private String host = "127.0.0.1";
        /**
         * canal server port
         */
        private int port = 10001;
        /**
         * canal user name
         */
        private String username = "";
        /**
         * canal password
         */
        private String password = "";
        /**
         * size when get messages from the canal server
         */
        private int batchSize = 1000;
        /**
         * filter
         */
        private String filter;
        /**
         * retry count when error occurred
         */
        private int retryCount = 10;
        /**
         * interval of the message-acquiring
         */
        private long acquireInterval = 1000;
    }
}

