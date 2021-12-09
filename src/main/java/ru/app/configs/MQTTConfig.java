package ru.app.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.app.components.MQTTPushClient;

@Component
@ConfigurationProperties("mqtt")
@Setter
@Getter
public class MQTTConfig {
    @Qualifier("MQTTPushClient")
    @Autowired
    private MQTTPushClient mqttPushClient;

    private String username;
    private String password;
    private String hostUrl;
    private String clientID;
    private String defaultTopic;
    private int timeout;
    private int keepalive;

    @Bean
    public MQTTPushClient getMqttPushClient() {
        System.out.println("hostUrl: " + hostUrl);
        System.out.println("clientID: " + clientID);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("timeout: " + timeout);
        System.out.println("keepalive: " + keepalive);
        mqttPushClient.connect(hostUrl, clientID, username, password, timeout, keepalive);
        // End with / / to subscribe to all topics starting with test
        //mqttPushClient.subscribe(defaultTopic, 0);
        return mqttPushClient;
    }
}
