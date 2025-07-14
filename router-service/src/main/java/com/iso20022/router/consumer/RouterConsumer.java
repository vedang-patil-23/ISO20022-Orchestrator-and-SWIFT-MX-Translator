package com.iso20022.router.consumer;

import com.iso20022.common.model.SwiftMessageDTO;
import com.iso20022.router.service.RouterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for handling incoming messages for routing.
 */
@Component
public class RouterConsumer {
    private static final Logger log = LoggerFactory.getLogger(RouterConsumer.class);

    private final RouterService routerService;

    public RouterConsumer(RouterService routerService) {
        this.routerService = routerService;
    }

    @KafkaListener(
        topics = "${kafka.topics.mx-out}",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(@Payload SwiftMessageDTO message) {
        try {
            log.info("Received message for routing: {}", message.getMessageId());
            routerService.routeMessage(message);
        } catch (Exception e) {
            log.error("Error processing message: {}", message.getMessageId(), e);
            // TODO: Handle error (e.g., send to DLQ)
        }
    }
}
