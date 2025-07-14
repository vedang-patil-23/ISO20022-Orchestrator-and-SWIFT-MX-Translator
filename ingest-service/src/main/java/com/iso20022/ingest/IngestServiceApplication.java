package com.iso20022.ingest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iso20022.ingest"})
public class IngestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IngestServiceApplication.class, args);
    }
}
