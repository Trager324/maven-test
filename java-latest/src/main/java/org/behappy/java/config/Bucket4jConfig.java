package org.behappy.java.config;

import org.behappy.java.config.refresher.Bucket4jConfigRefresher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;


@Component
@ConfigurationProperties(prefix = Bucket4jConfig.BUCKET4J_CONFIG)
@PropertySource(value = "file:" + Bucket4jConfigRefresher.BUCKET4J_CONFIG_PATH + Bucket4jConfigRefresher.FILE_NAME,
        ignoreResourceNotFound = true)
//@RefreshScope
//@Scope("prototype")
public class Bucket4jConfig {
    /**
     * spring配置前缀
     */
    public static final String BUCKET4J_CONFIG = "bucket4j";

    /**
     * 是否开启bucket4j限流
     */
    private boolean enabled = true;
    /**
     * 令牌桶上限
     */
    private int capacity = 20;
    /**
     * 每次补充的令牌数量
     */
    private int tokens = 10;
    /**
     * 补充令牌的时间间隔
     */
    private int time = 1;
    /**
     * 补充令牌的时间单位
     */
    private ChronoUnit unit = ChronoUnit.SECONDS;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ChronoUnit getUnit() {
        return unit;
    }

    public void setUnit(ChronoUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Bucket4jConfig{" +
               "enabled=" + enabled +
               ", capacity=" + capacity +
               ", tokens=" + tokens +
               ", time=" + time +
               ", unit=" + unit +
               '}';
    }
}
