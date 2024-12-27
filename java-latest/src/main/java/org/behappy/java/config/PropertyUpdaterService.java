package org.behappy.java.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PropertyUpdaterService {
    private static final String DYNAMIC_PROPERTIES_SOURCE_NAME = "dynamicProperties";

    private final ConfigurableEnvironment environment;

    public void updateProperty(String key, String value) {
        MutablePropertySources propertySources = environment.getPropertySources();
        if (propertySources.get(DYNAMIC_PROPERTIES_SOURCE_NAME) instanceof MapPropertySource propertySource) {
            propertySource.getSource().put(key, value);
        } else {
            Map<String, Object> dynamicProperties = new HashMap<>();
            dynamicProperties.put(key, value);
            propertySources.addFirst(new MapPropertySource(DYNAMIC_PROPERTIES_SOURCE_NAME, dynamicProperties));
        }
    }
}
