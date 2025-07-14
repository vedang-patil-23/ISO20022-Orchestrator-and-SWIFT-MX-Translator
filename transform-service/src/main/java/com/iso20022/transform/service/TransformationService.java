package com.iso20022.transform.service;

import com.iso20022.common.model.SwiftMessageDTO;
import com.iso20022.transform.mapper.MT103ToPacs008Mapper;
import com.iso20022.transform.model.MXMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;

@Service
public class TransformationService {
    private static final Logger log = LoggerFactory.getLogger(TransformationService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MT103ToPacs008Mapper mt103ToPacs008Mapper;
    private final String mxOutTopic = "mx.out";

    public TransformationService(KafkaTemplate<String, Object> kafkaTemplate, 
                               MT103ToPacs008Mapper mt103ToPacs008Mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mt103ToPacs008Mapper = mt103ToPacs008Mapper;
    }

    public void transformAndRoute(SwiftMessageDTO swiftMessage) {
        try {
            log.info("Transforming message: {}", swiftMessage.getMessageId());
            
            // 1. Map SWIFT MT to ISO 20022 MX
            MXMessage mxMessage = mapToMX(swiftMessage);
            
            // 2. Convert to XML
            String mxXml = convertToXml(mxMessage);
            
            // 3. Update status
            swiftMessage.setStatus("TRANSFORMED");
            swiftMessage.setContent(mxXml);
            
            // 4. Send to Kafka
            kafkaTemplate.send(mxOutTopic, swiftMessage.getMessageId(), swiftMessage);
            log.info("Successfully transformed message: {}", swiftMessage.getMessageId());
            
        } catch (Exception e) {
            log.error("Error transforming message: {}", swiftMessage.getMessageId(), e);
            swiftMessage.setStatus("TRANSFORMATION_FAILED");
            swiftMessage.setValidationError(e.getMessage());
            // TODO: Send to DLQ or error topic
        }
    }
    
    private MXMessage mapToMX(SwiftMessageDTO swiftMessage) {
        // TODO: Implement mapping based on message type
        // For now, we'll just map MT103 to pacs.008
        return mt103ToPacs008Mapper.map(swiftMessage);
    }
    
    private String convertToXml(MXMessage mxMessage) throws Exception {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(mxMessage.getDocumentClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            StringWriter writer = new StringWriter();
            marshaller.marshal(mxMessage.getDocument(), writer);
            return writer.toString();
        } catch (Exception e) {
            log.error("Error converting to XML: {}", e.getMessage(), e);
            throw new Exception("Failed to convert to XML: " + e.getMessage(), e);
        }
    }
}
