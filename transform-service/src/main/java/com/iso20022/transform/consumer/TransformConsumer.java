package com.iso20022.transform.consumer;

import com.iso20022.common.model.SwiftMessageDTO;
import com.iso20022.transform.service.TransformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TransformConsumer {
    private static final Logger log = LoggerFactory.getLogger(TransformConsumer.class);
    
    private final TransformationService transformationService;

    public TransformConsumer(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @KafkaListener(
        topics = "${kafka.topics.mt-valid}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(@Payload SwiftMessageDTO message) {
        try {
            log.info("Received message for transformation: {}", message.getMessageId());
            transformationService.transformAndRoute(message);
        } catch (Exception e) {
            log.error("Error processing message: {}", message.getMessageId(), e);
            // TODO: Handle error (e.g., send to DLQ)
        }
    }
}
