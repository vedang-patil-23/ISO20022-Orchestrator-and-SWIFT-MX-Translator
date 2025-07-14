package com.iso20022.router.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.Instant;
import java.util.Objects;

@Document
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    
    private String messageId;
    private Instant timestamp;
    private String status;
    private String errorMessage;
    private String messageType;
    private String sender;
    private String receiver;
    private String rawContent;
    private String transformedContent;
    private String serviceName = "router-service";
    private String environment = "development";

    public AuditLog() {
    }

    public AuditLog(String id, String messageId, Instant timestamp, String status, 
                   String errorMessage, String messageType, String sender, 
                   String receiver, String rawContent, String transformedContent, 
                   String serviceName, String environment) {
        this.id = id;
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.status = status;
        this.errorMessage = errorMessage;
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.rawContent = rawContent;
        this.transformedContent = transformedContent;
        if (serviceName != null) this.serviceName = serviceName;
        if (environment != null) this.environment = environment;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getTransformedContent() {
        return transformedContent;
    }

    public void setTransformedContent(String transformedContent) {
        this.transformedContent = transformedContent;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return Objects.equals(id, auditLog.id) &&
               Objects.equals(messageId, auditLog.messageId) &&
               Objects.equals(timestamp, auditLog.timestamp) &&
               Objects.equals(status, auditLog.status) &&
               Objects.equals(errorMessage, auditLog.errorMessage) &&
               Objects.equals(messageType, auditLog.messageType) &&
               Objects.equals(sender, auditLog.sender) &&
               Objects.equals(receiver, auditLog.receiver) &&
               Objects.equals(rawContent, auditLog.rawContent) &&
               Objects.equals(transformedContent, auditLog.transformedContent) &&
               Objects.equals(serviceName, auditLog.serviceName) &&
               Objects.equals(environment, auditLog.environment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageId, timestamp, status, errorMessage, 
                          messageType, sender, receiver, rawContent, 
                          transformedContent, serviceName, environment);
    }

    @Override
    public String toString() {
        return "AuditLog{" +
               "id='" + id + '\'' +
               ", messageId='" + messageId + '\'' +
               ", timestamp=" + timestamp +
               ", status='" + status + '\'' +
               ", errorMessage='" + errorMessage + '\'' +
               ", messageType='" + messageType + '\'' +
               ", sender='" + sender + '\'' +
               ", receiver='" + receiver + '\'' +
               ", rawContent='" + (rawContent != null ? "[content]" : "null") + '\'' +
               ", transformedContent='" + (transformedContent != null ? "[content]" : "null") + '\'' +
               ", serviceName='" + serviceName + '\'' +
               ", environment='" + environment + '\'' +
               '}';
    }

    // Builder pattern implementation
    public static AuditLogBuilder builder() {
        return new AuditLogBuilder();
    }

    public static class AuditLogBuilder {
        private String id;
        private String messageId;
        private Instant timestamp;
        private String status;
        private String errorMessage;
        private String messageType;
        private String sender;
        private String receiver;
        private String rawContent;
        private String transformedContent;
        private String serviceName = "router-service";
        private String environment = "development";

        public AuditLogBuilder id(String id) {
            this.id = id;
            return this;
        }

        public AuditLogBuilder messageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public AuditLogBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public AuditLogBuilder status(String status) {
            this.status = status;
            return this;
        }

        public AuditLogBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public AuditLogBuilder messageType(String messageType) {
            this.messageType = messageType;
            return this;
        }

        public AuditLogBuilder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public AuditLogBuilder receiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public AuditLogBuilder rawContent(String rawContent) {
            this.rawContent = rawContent;
            return this;
        }

        public AuditLogBuilder transformedContent(String transformedContent) {
            this.transformedContent = transformedContent;
            return this;
        }

        public AuditLogBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public AuditLogBuilder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public AuditLog build() {
            return new AuditLog(id, messageId, timestamp, status, errorMessage, 
                              messageType, sender, receiver, rawContent, 
                              transformedContent, serviceName, environment);
        }
    }
}
