package com.iso20022.ingest.model;

import java.util.Objects;

public class UploadResponse {
    private String message;
    private String messageId;
    private String errorDetails;

    public UploadResponse() {
    }

    public UploadResponse(String message, String messageId) {
        this.message = message;
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadResponse that = (UploadResponse) o;
        return Objects.equals(message, that.message) &&
               Objects.equals(messageId, that.messageId) &&
               Objects.equals(errorDetails, that.errorDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, messageId, errorDetails);
    }

    @Override
    public String toString() {
        return "UploadResponse{" +
               "message='" + message + '\'' +
               ", messageId='" + messageId + '\'' +
               ", errorDetails='" + errorDetails + '\'' +
               '}';
    }
}
