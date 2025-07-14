package com.iso20022.router.service;

import com.iso20022.common.model.SwiftMessageDTO;
import com.iso20022.router.config.RoutingProperties;
import com.iso20022.router.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Service responsible for routing messages to appropriate destinations.
 */
@Service
public class RouterService {
    private static final Logger log = LoggerFactory.getLogger(RouterService.class);

    private final WebClient webClient;
    private final RoutingProperties routingProperties;
    private final AuditService auditService;

    public RouterService(WebClient webClient, 
                        RoutingProperties routingProperties, 
                        AuditService auditService) {
        this.webClient = webClient;
        this.routingProperties = routingProperties;
        this.auditService = auditService;
    }

    @Retryable(
        value = { WebClientResponseException.class },
        maxAttemptsExpression = "${settlement.api.retry.max-attempts}",
        backoff = @Backoff(
            delayExpression = "${settlement.api.retry.initial-interval}",
            multiplierExpression = "${settlement.api.retry.multiplier}",
            maxDelayExpression = "${settlement.api.retry.max-interval}"
        )
    )
    public void routeMessage(SwiftMessageDTO message) {
        try {
            log.info("Routing message: {}", message.getMessageId());
            
            // 1. Find matching route
            Route route = findMatchingRoute(message);
            if (route == null) {
                log.warn("No matching route found for message: {}", message.getMessageId());
                updateMessageStatus(message, "NO_MATCHING_ROUTE", "No matching route found");
                return;
            }

            // 2. Prepare request
            String url = routingProperties.getSettlement().getBaseUrl() + route.getEndpoint();
            // Convert method string to HttpMethod
            HttpMethod method;
            try {
                method = HttpMethod.valueOf(route.getMethod().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid HTTP method: " + route.getMethod());
            }

            // 3. Send request
            webClient.method(method)
                    .uri(url)
                    .bodyValue(message.getContent())
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(10))
                    .doOnSuccess(response -> {
                        log.info("Successfully routed message: {}", message.getMessageId());
                        updateMessageStatus(message, "ROUTED", "Successfully routed to " + route.getDestination());
                    })
                    .doOnError(error -> {
                        log.error("Error routing message: {}", message.getMessageId(), error);
                        updateMessageStatus(message, "ROUTING_FAILED", "Failed to route: " + error.getMessage());
                    })
                    .subscribe();
            
        } catch (Exception e) {
            log.error("Unexpected error routing message: {}", message.getMessageId(), e);
            updateMessageStatus(message, "ERROR", "Unexpected error: " + e.getMessage());
            throw e;
        }
    }

    private Route findMatchingRoute(SwiftMessageDTO message) {
        // Extract message type
        String messageType = extractMessageType(message);
        if (messageType == null) {
            log.warn("Could not determine message type for message: {}", message.getMessageId());
            return null;
        }
        
        // Find first matching route based on message type pattern
        if (routingProperties.getRules() == null) {
            log.warn("No routing rules configured");
            return null;
        }
        
        return routingProperties.getRules().stream()
            .filter(route -> route != null && route.getPattern() != null && messageType.matches(route.getPattern()))
            .findFirst()
            .orElse(null);
    }
    
    private String extractMessageType(SwiftMessageDTO message) {
        // First try to get the message type from the message itself
        if (message.getMessageType() != null && !message.getMessageType().isEmpty()) {
            return message.getMessageType();
        }
        
        // If not available, try to extract from content
        String content = message.getContent();
        if (content != null && content.length() >= 3) {
            // Simple extraction - first 3 characters of SWIFT message is typically the message type
            return content.substring(0, 3);
        }
        
        return null;
    }
    
    private void updateMessageStatus(SwiftMessageDTO message, String status, String details) {
        message.setStatus(status);
        if (details != null) {
            message.setValidationError(details);
        }
        auditService.logMessageStatus(message);
    }
}
