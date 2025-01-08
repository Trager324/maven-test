package org.behappy.java.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class PropertyController {
    private final Bucket4jConfig bucket4jConfig;

    @GetMapping
    public String get() {
        log.info("Bucket4jConfig: {}", bucket4jConfig);
        return bucket4jConfig.toString();
    }

    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        if (RefreshScopeRefreshedEvent.DEFAULT_NAME.equals(event.getName())) {
            log.info("Bucket4jConfig refreshed: {}", bucket4jConfig);
        }
    }
}