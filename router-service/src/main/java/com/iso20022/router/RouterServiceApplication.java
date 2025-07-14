package com.iso20022.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableScheduling
public class RouterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RouterServiceApplication.class, args);
    }
}
