package com.iso20022.transform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class TransformServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransformServiceApplication.class, args);
    }
}
