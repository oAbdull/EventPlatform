package org.example.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue eventQueue() {
        return new Queue("event.queue", false);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("reply.queue", false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("event.exchange");
    }

    @Bean
    public Binding binding(Queue eventQueue, DirectExchange exchange) {
        return BindingBuilder.bind(eventQueue).to(exchange).with("event.routingkey");
    }
}