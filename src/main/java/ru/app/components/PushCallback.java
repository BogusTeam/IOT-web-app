package ru.app.components;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.app.configs.MQTTConfig;

@Component
public class PushCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MQTTPushClient.class);

    @Autowired
    private MQTTConfig mqttConfig;

    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        // After the connection is lost, it is usually reconnected here
        logger.info("Disconnected, can be reconnected");
    }

    @Override
    public void mqttErrorOccurred(MqttException exception) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        // The message you get after you subscribe will be executed here
        logger.info("Receive message subject : " + topic);
        //logger.info("Receive message's Qos : " + mqttMessage.getQos());
        logger.info("Receive message content : " + new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttToken token) {
        logger.info("deliveryComplete --------- " + token.isComplete());
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {

    }

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {

    }
}