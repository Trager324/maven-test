package org.behappy.java.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PropertyController {
    private final PropertyUpdaterService propertyUpdaterService;

    private final ExampleBean exampleBean;

    @PostMapping("/update")
    public String updateProperty(
            @RequestParam("key") String key,
            @RequestParam("value") String value) {
        propertyUpdaterService.updateProperty(key, value);
        return "Property updated. Remember to call the actuator /actuator/refresh";
    }

    @GetMapping("/customProperty")
    public String getCustomProperty() {
        return exampleBean.getCustomProperty();
    }
}