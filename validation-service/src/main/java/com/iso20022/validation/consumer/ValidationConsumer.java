package com.iso20022.validation.consumer;

import com.iso20022.common.model.SwiftMessageDTO;
import com.iso20022.validation.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ValidationConsumer {

    private static final Logger log = LoggerFactory.getLogger(ValidationConsumer.class);
    private final ValidationService validationService;

    public ValidationConsumer(ValidationService validationService) {
        this.validationService = validationService;
    }

    @KafkaListener(
        topics = "${kafka.topics.mt-raw}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(@Payload SwiftMessageDTO message) {
        try {
            log.info("Received message for validation: {}", message.getMessageId());
            validationService.validateAndRoute(message);
        } catch (Exception e) {
            log.error("Error processing message: {}", message.getMessageId(), e);
        }
    }
}
