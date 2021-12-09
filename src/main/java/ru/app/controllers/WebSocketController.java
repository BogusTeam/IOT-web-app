package ru.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.app.components.WSMessage;
import ru.app.services.MQTTService;

@Controller
public class WebSocketController {
    @Autowired
    private MQTTService mqttService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public void processMessage(WSMessage message) {
        mqttService.checkMessage(message);
    }
}
