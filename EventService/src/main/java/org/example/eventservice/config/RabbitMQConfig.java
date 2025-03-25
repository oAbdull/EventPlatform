package org.example.eventservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue("test.queue", false);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("reply.queue", false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("test.exchange");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("test.routingkey");
    }
}