package ru.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.app.components.MQTTPushClient;
import ru.app.components.WSMessage;
import ru.app.configs.MQTTConfig;
import ru.app.configs.SensorsConfig;

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

    @Autowired
    private SensorsConfig sensorsConfig;

    public void checkMessage(WSMessage message) {
        String datetime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        String device = "";
        String token = null;
        String msg = "";
        if (message.getDeviceId() == 0) {
            device = "motion";
            token = sensorsConfig.getMotion();
            msg = "{" + MessageFormat.format("\"{0}\": \"{1}\", \"datetime\": \"{1}\", \"id\": \"10\"",
                    device, message.getValue(), datetime) + "}";
        } else if (message.getDeviceId() == 1) {
            device = "temperature";
        } else if (message.getDeviceId() == 2) {
            device = "voltage";
            token = sensorsConfig.getVoltage();
            msg = "{" + MessageFormat.format("\"{0}\": \"{1}\", \"datetime\": \"{1}\", \"id\": \"12\"",
                    device, message.getValue(), datetime) + "}";
        }
        mqttConfig.getMqttPushClient(token);
        mqttPushClient.publish(0, false, mqttConfig.getDefaultTopic(), msg);
    }
}
