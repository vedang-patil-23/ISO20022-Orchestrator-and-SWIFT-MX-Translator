package com.iso20022.ingest.model;

import java.time.Instant;
import java.util.Objects;

public class SwiftMessageDTO {
    private String id;
    private String originalFilename;
    private String messageType;
    private String sender;
    private String receiver;
    private String rawContent;
    private Instant timestamp;
    private String status;
    private String errorMessage;

    public SwiftMessageDTO() {
    }

    public SwiftMessageDTO(String id, String originalFilename, String messageType, String sender, 
                          String receiver, String rawContent, Instant timestamp, String status, 
                          String errorMessage) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.rawContent = rawContent;
        this.timestamp = timestamp;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    // Builder pattern implementation
    public static SwiftMessageDTOBuilder builder() {
        return new SwiftMessageDTOBuilder();
    }

    public static class SwiftMessageDTOBuilder {
        private String id;
        private String originalFilename;
        private String messageType;
        private String sender;
        private String receiver;
        private String rawContent;
        private Instant timestamp;
        private String status;
        private String errorMessage;

        public SwiftMessageDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SwiftMessageDTOBuilder originalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
            return this;
        }

        public SwiftMessageDTOBuilder messageType(String messageType) {
            this.messageType = messageType;
            return this;
        }

        public SwiftMessageDTOBuilder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public SwiftMessageDTOBuilder receiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public SwiftMessageDTOBuilder rawContent(String rawContent) {
            this.rawContent = rawContent;
            return this;
        }

        public SwiftMessageDTOBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public SwiftMessageDTOBuilder status(String status) {
            this.status = status;
            return this;
        }

        public SwiftMessageDTOBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public SwiftMessageDTO build() {
            return new SwiftMessageDTO(id, originalFilename, messageType, sender, receiver, 
                                     rawContent, timestamp, status, errorMessage);
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwiftMessageDTO that = (SwiftMessageDTO) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(originalFilename, that.originalFilename) &&
               Objects.equals(messageType, that.messageType) &&
               Objects.equals(sender, that.sender) &&
               Objects.equals(receiver, that.receiver) &&
               Objects.equals(rawContent, that.rawContent) &&
               Objects.equals(timestamp, that.timestamp) &&
               Objects.equals(status, that.status) &&
               Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalFilename, messageType, sender, receiver, 
                           rawContent, timestamp, status, errorMessage);
    }

    @Override
    public String toString() {
        return "SwiftMessageDTO{" +
               "id='" + id + '\'' +
               ", originalFilename='" + originalFilename + '\'' +
               ", messageType='" + messageType + '\'' +
               ", sender='" + sender + '\'' +
               ", receiver='" + receiver + '\'' +
               ", rawContent='" + rawContent + '\'' +
               ", timestamp=" + timestamp +
               ", status='" + status + '\'' +
               ", errorMessage='" + errorMessage + '\'' +
               '}';
    }
    }
