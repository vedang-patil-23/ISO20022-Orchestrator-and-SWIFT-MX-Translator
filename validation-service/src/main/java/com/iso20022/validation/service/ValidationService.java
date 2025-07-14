package com.iso20022.validation.service;

import com.iso20022.common.model.SwiftMessageDTO;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ValidationService {

    private static final Logger log = LoggerFactory.getLogger(ValidationService.class);
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String mtValidTopic = "mt.valid";
    private final String mtInvalidTopic = "mt.invalid";

    public ValidationService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void validateAndRoute(SwiftMessageDTO message) {
        try {
            // 1. Parse the SWIFT message
            SwiftMessage swiftMessage = parseSwiftMessage(message.getContent());
            
            // 2. Validate against XSD schema
            boolean isValid = validateAgainstXSD(swiftMessage.message());
            
            // 3. Update message status
            message.setStatus(isValid ? "VALIDATED" : "VALIDATION_FAILED");
            
            // 4. Route based on validation result
            if (isValid) {
                log.info("Message {} validated successfully", message.getMessageId());
                kafkaTemplate.send(mtValidTopic, message.getMessageId(), message);
            } else {
                log.warn("Message {} failed validation", message.getMessageId());
                kafkaTemplate.send(mtInvalidTopic, message.getMessageId(), message);
            }
            
        } catch (Exception e) {
            log.error("Error validating message: {}", message.getMessageId(), e);
            message.setStatus("VALIDATION_ERROR");
            message.setValidationError(e.getMessage());
            kafkaTemplate.send(mtInvalidTopic, message.getMessageId(), message);
        }
    }
    
    private SwiftMessage parseSwiftMessage(String content) {
        try {
            return SwiftMessage.parse(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse SWIFT message", e);
        }
    }
    
    private boolean validateAgainstXSD(String message) {
        try {
            // Create SchemaFactory capable of understanding W3C XML Schema
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            // Load the XSD schema
            Source schemaSource = new StreamSource(getClass().getResourceAsStream("/xsd/iso20022/pain.001.001.03.xsd"));
            Schema schema = factory.newSchema(schemaSource);
            
            // Create a Validator instance, which can be used to validate an instance document
            Validator validator = schema.newValidator();
            
            // Validate the XML message against the schema
            validator.validate(new StreamSource(new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8))));
            
            return true;
        } catch (Exception e) {
            log.warn("XSD validation failed: {}", e.getMessage());
            return false;
        }
    }
}
