package org.behappy.java.config.refresher;

import org.behappy.java.config.watcher.SimpleFileWatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RefresherRegister implements InitializingBean {
    private final List<SpringConfigRefresher> refreshers;

    @Autowired
    public RefresherRegister(List<SpringConfigRefresher> refreshers) {
        this.refreshers = refreshers;
    }

    @Override
    public void afterPropertiesSet() {
        // 根据监听目录分组，复用WatchService
        Map<Path, List<SpringConfigRefresher>> grouping = this.refreshers.stream()
                .collect(Collectors.groupingBy(SpringConfigRefresher::getConfigPath));
        grouping.forEach((path, refreshers) -> {
            SimpleFileWatcher watcher = new SimpleFileWatcher(path, refreshers);
            watcher.start();
        });
    }
}
