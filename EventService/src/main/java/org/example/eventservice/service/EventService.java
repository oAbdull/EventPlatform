// EventService/src/main/java/org/example/eventservice/service/EventService.java
package org.example.eventservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String processTestEvent() {
        String message = "Test event message from Event Service";

        String response = (String) rabbitTemplate.convertSendAndReceive(
                "event-exchange",
                "event.test",
                message
        );

        if (response == null) {
            return "No response received from messaging system";
        }

        return response;
    }
}