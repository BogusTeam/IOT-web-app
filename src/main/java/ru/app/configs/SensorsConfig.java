package ru.app.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sensors")
@Setter
@Getter
public class SensorsConfig {
    private String motion;
    private String voltage;
}
