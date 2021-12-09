package ru.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.app.components.MQTTPushClient;
import ru.app.components.WSMessage;
import ru.app.configs.MQTTConfig;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class MQTTService {
    @Qualifier("MQTTPushClient")
    @Autowired
    private MQTTPushClient mqttPushClient;

    @Autowired
    private MQTTConfig mqttConfig;

    public void checkMessage(WSMessage message) {
        String datetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        String device = "";
        if (message.getDeviceId() == 0) {
            device = "motion";
        } else if (message.getDeviceId() == 1) {
            device = "temperature";
        } else if (message.getDeviceId() == 2) {
            device = "voltage";
        }
        mqttPushClient.publish(0, false, mqttConfig.getDefaultTopic(),
                "{" + MessageFormat.format("\"device\": \"{0}\", \"value\": \"{1}\", \"datetime\": \"{2}\"",
                        device, message.getValue(), datetime) + "}");
    }
}
