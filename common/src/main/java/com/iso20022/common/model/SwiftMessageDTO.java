package com.iso20022.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwiftMessageDTO {
    private String messageId;
    private String originalFilename;
    private String messageType;
    private String sender;
    private String receiver;
    private String content;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant timestamp;
    private String status;
    private String validationError;

    public SwiftMessageDTO() {
        // Default constructor
    }

    public SwiftMessageDTO(String messageId, String originalFilename, String messageType, 
                          String sender, String receiver, String content, 
                          Instant timestamp, String status, String validationError) {
        this.messageId = messageId;
        this.originalFilename = originalFilename;
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
        this.status = status;
        this.validationError = validationError;
    }

    // Getters and Setters
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwiftMessageDTO that = (SwiftMessageDTO) o;
        return Objects.equals(messageId, that.messageId) &&
               Objects.equals(originalFilename, that.originalFilename) &&
               Objects.equals(messageType, that.messageType) &&
               Objects.equals(sender, that.sender) &&
               Objects.equals(receiver, that.receiver) &&
               Objects.equals(content, that.content) &&
               Objects.equals(timestamp, that.timestamp) &&
               Objects.equals(status, that.status) &&
               Objects.equals(validationError, that.validationError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, originalFilename, messageType, sender, receiver, 
                          content, timestamp, status, validationError);
    }

    @Override
    public String toString() {
        return "SwiftMessageDTO{" +
               "messageId='" + messageId + '\'' +
               ", originalFilename='" + originalFilename + '\'' +
               ", messageType='" + messageType + '\'' +
               ", sender='" + sender + '\'' +
               ", receiver='" + receiver + '\'' +
               ", content='" + (content != null ? "[content]" : null) + '\'' +
               ", timestamp=" + timestamp +
               ", status='" + status + '\'' +
               ", validationError='" + validationError + '\'' +
               '}';
    }
}
