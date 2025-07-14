package com.iso20022.router.service;

import com.iso20022.common.model.SwiftMessageDTO;
import com.iso20022.router.model.AuditLog;
import com.iso20022.router.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Service for auditing message processing operations.
 */
@Service
public class AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Logs the status of a processed message to the audit log.
     *
     * @param message The message to log
     */
    public void logMessageStatus(SwiftMessageDTO message) {
        if (message == null) {
            log.warn("Cannot log null message");
            return;
        }
        
        try {
            AuditLog logEntry = new AuditLog();
            logEntry.setMessageId(message.getMessageId());
            logEntry.setTimestamp(Instant.now());
            logEntry.setStatus(message.getStatus());
            logEntry.setErrorMessage(message.getValidationError());
            logEntry.setMessageType(message.getMessageType());
            logEntry.setSender(message.getSender());
            logEntry.setReceiver(message.getReceiver());
            logEntry.setRawContent(message.getContent());
            
            // In this implementation, we don't have a separate transformed content field
            // If needed, we can store the same content or leave it null
            logEntry.setTransformedContent(null);
            
            auditLogRepository.save(logEntry);
            log.debug("Audit log saved for message: {}", message.getMessageId());
            
        } catch (Exception e) {
            log.error("Failed to save audit log for message: {}", message.getMessageId(), e);
        }
    }
}
