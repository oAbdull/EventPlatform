package org.eventservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TICKET_QUEUE_NAME = "event.created.queue";
    public static final String TICKET_EXCHANGE_NAME = "event.exchange";
    public static final String TICKET_ROUTING_KEY = "event.created";

    public static final String USER_QUEUE_NAME = "user.event.notification.queue";
    public static final String USER_EXCHANGE_NAME = "user.exchange";
    public static final String USER_ROUTING_KEY = "user.event.notification";

    public static final String EVENT_QUEUE_NAME = "event.queue"; // Add event.queue
    public static final String EVENT_EXCHANGE_NAME = "event.exchange";
    public static final String EVENT_ROUTING_KEY = "event.created";

    @Bean
    public Queue ticketQueue() {
        return new Queue(TICKET_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange ticketExchange() {
        return new TopicExchange(TICKET_EXCHANGE_NAME);
    }

    @Bean
    public Binding ticketBinding(Queue ticketQueue, TopicExchange ticketExchange) {
        return BindingBuilder.bind(ticketQueue).to(ticketExchange).with(TICKET_ROUTING_KEY);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE_NAME);
    }

    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(USER_ROUTING_KEY);
    }

    @Bean
    public Queue eventQueue() {
        return new Queue(EVENT_QUEUE_NAME, true); // Declare event.queue
    }

    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(EVENT_EXCHANGE_NAME); // Use TopicExchange
    }

    @Bean
    public Binding eventBinding(Queue eventQueue, TopicExchange eventExchange) {
        return BindingBuilder.bind(eventQueue).to(eventExchange).with(EVENT_ROUTING_KEY);
    }
}