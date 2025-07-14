package com.iso20022.transform.model;

/**
 * Represents an ISO 20022 MX message with its metadata
 */
public class MXMessage {
    
    /**
     * The original message ID from the SWIFT message
     */
    private String messageId;
    
    /**
     * The document type (e.g., "pacs.008.001.08")
     */
    private String documentType;
    
    /**
     * The original SWIFT message content
     */
    private String originalMessage;
    
    /**
     * The transformed XML content
     */
    private String transformedContent;
    
    /**
     * The JAXB document object
     */
    private transient Object document;

    public MXMessage() {
    }

    public MXMessage(String messageId, String documentType, String originalMessage, 
                    String transformedContent, Object document) {
        this.messageId = messageId;
        this.documentType = documentType;
        this.originalMessage = originalMessage;
        this.transformedContent = transformedContent;
        this.document = document;
    }

    // Getters and Setters
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getTransformedContent() {
        return transformedContent;
    }

    public void setTransformedContent(String transformedContent) {
        this.transformedContent = transformedContent;
    }

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
        this.document = document;
    }
    
    /**
     * Gets the JAXB document class for this message type
     */
    @SuppressWarnings("unchecked")
    public Class<?> getDocumentClass() {
        if (documentType == null) {
            throw new IllegalStateException("Document type not set");
        }
        
        // This would be replaced with actual JAXB class loading
        // For example: return DocumentClassRegistry.getClass(documentType);
        return Object.class;
    }
}
