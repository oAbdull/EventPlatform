package org.example.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue testQueue() {
        return new Queue("test.queue", false);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("reply.queue", false);
    }

    @Bean
    public Queue eventQueue() { // Add this bean
        return new Queue("event-queue", false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("test.exchange");
    }

    @Bean
    public Binding binding(Queue testQueue, DirectExchange exchange) {
        return BindingBuilder.bind(testQueue).to(exchange).with("test.routingkey");
    }
}