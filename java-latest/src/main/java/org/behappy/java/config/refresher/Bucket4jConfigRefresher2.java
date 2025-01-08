package org.behappy.java.config.refresher;
 
import org.behappy.java.config.PropertyUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ConfigDataContextRefresher;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Bucket4jConfigRefresher2 extends SpringConfigRefresher {
    public static final String BUCKET4J_CONFIG_PATH = "./java17/";

    @Autowired
    public Bucket4jConfigRefresher2(
            PropertyUpdaterService propertyUpdaterService,
            ConfigDataContextRefresher configDataContextRefresher) {
        super(propertyUpdaterService, configDataContextRefresher);
    }

    @Override
    public String filename() {
        return "bucket4j-config.yml";
    }

    @Override
    public Path getConfigPath() {
        return Paths.get(BUCKET4J_CONFIG_PATH);
    }
}
