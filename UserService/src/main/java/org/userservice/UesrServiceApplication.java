package org.userservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class UesrServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UesrServiceApplication.class, args);
    }
}