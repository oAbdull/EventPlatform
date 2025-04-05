package org.example.ticketservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TICKET_QUEUE_NAME = "event.created.queue";
    public static final String TICKET_EXCHANGE_NAME = "event.exchange";
    public static final String TICKET_ROUTING_KEY = "event.created";

    @Bean
    public Queue ticketQueue() {
        return new Queue(TICKET_QUEUE_NAME, true); // Declare the queue
    }

    @Bean
    public TopicExchange ticketExchange() {
        return new TopicExchange(TICKET_EXCHANGE_NAME);
    }

    @Bean
    public Binding ticketBinding(Queue ticketQueue, TopicExchange ticketExchange) {
        return BindingBuilder.bind(ticketQueue).to(ticketExchange).with(TICKET_ROUTING_KEY);
    }
}