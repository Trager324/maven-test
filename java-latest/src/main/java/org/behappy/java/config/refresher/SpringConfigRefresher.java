package org.behappy.java.config.refresher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.behappy.java.config.PropertyUpdaterService;
import org.behappy.java.config.watcher.ControlType;
import org.behappy.java.config.watcher.StandardWatchEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ConfigDataContextRefresher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public abstract class SpringConfigRefresher implements StandardWatchEventListener<Path> {
    public static final Path CONFIG_PATH = Paths.get(".");
    private static final Log log = LogFactory.getLog(SpringConfigRefresher.class);

    private final PropertyUpdaterService propertyUpdaterService;
    private final ConfigDataContextRefresher configDataContextRefresher;

    @Autowired
    public SpringConfigRefresher(
            PropertyUpdaterService propertyUpdaterService,
            ConfigDataContextRefresher configDataContextRefresher) {
        this.propertyUpdaterService = propertyUpdaterService;
        this.configDataContextRefresher = configDataContextRefresher;
    }

    public abstract String filename();

    public Path getConfigPath() {
        return CONFIG_PATH;
    }

    @Override
    public ControlType onDelete(Path path) {
        // 自测时监听目录会由于cp -r与rm -rf共同作用导致监听失效
        // 通过监听配置文件和监听目录的删除事件并控制监听器重置WatchService来重新生效
        // 自测在mac环境中不存在该问题，可能是WatchService的平台相关问题
        return filename().equals(path.getFileName().toString())
                ? ControlType.BREAK : ControlType.CONTINUE;
    }

    @Override
    public ControlType onModify(Path path) throws Exception {
        Properties props = new Properties();
        props.load(Files.newInputStream(getConfigPath().resolve(filename())));
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            propertyUpdaterService.updateProperty(key, value);
        }
        afterRefresh(props);
        return ControlType.CONTINUE;
    }

    private void fireRefreshEvent(Properties props) {
        configDataContextRefresher.refresh();
        log.debug("Config updated: " + props);
    }

    protected void afterRefresh(Properties props) {
        this.fireRefreshEvent(props);
    }
}
