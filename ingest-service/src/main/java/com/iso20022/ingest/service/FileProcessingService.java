package com.iso20022.ingest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileProcessingService {
    private static final Logger log = LoggerFactory.getLogger(FileProcessingService.class);
    
    public Map<String, Object> processUploadedFile(MultipartFile file) throws IOException {
        String messageId = generateMessageId();
        String originalFilename = file.getOriginalFilename();
        
        try {
            // Basic file info
            Map<String, Object> fileInfo = new HashMap<>();
            fileInfo.put("id", messageId);
            fileInfo.put("filename", originalFilename);
            fileInfo.put("contentType", file.getContentType());
            fileInfo.put("size", file.getSize());
            fileInfo.put("timestamp", Instant.now().toString());
            fileInfo.put("status", "RECEIVED");
            
            // For demo purposes, just log the first 100 chars of the file
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            int previewLength = Math.min(100, fileContent.length());
            fileInfo.put("preview", fileContent.substring(0, previewLength) + 
                (fileContent.length() > 100 ? "..." : ""));
            
            log.info("Processed file: {}", originalFilename);
            return fileInfo;
            
        } catch (Exception e) {
            log.error("Error processing file: {}", e.getMessage(), e);
            throw new IOException("Failed to process file: " + e.getMessage(), e);
        }
    }
    
    private String generateMessageId() {
        return "MSG_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }
}
