package org.eventservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventserviceApplication.class, args);
    }

}
