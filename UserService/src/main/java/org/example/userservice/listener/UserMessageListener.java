package org.example.userservice.listener;

import org.example.userservice.config.RabbitMQConfig;
import org.example.userservice.dto.UserEventNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserMessageListener {

    private static final Logger log = LoggerFactory.getLogger(UserMessageListener.class);

    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE_NAME)
    public void handleEventNotification(UserEventNotification notification) {
        log.info("Received event notification: userId={}, eventId={}, eventName={}",
                notification.getUserId(), notification.getEventId(), notification.getEventName());
        // Add logic to process the notification (e.g., update user record, send email)
    }
}