package com.iso20022.ingest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ingest")
public class FileUploadController {
    private static final String UPLOAD_DIR = "uploads";

    public FileUploadController() {
        // Default constructor
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> handleFileUpload(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "storeLocally", required = false, defaultValue = "true") boolean storeLocally) {
        
        Map<String, Object> response = new LinkedHashMap<>();
        
        if (files == null || files.length == 0) {
            response.put("success", false);
            response.put("message", "No files were uploaded");
            return ResponseEntity.badRequest().body(response);
        }
        
        List<Map<String, String>> processedFiles = new ArrayList<>();
        List<Map<String, String>> failedFiles = new ArrayList<>();
        
        // Ensure upload directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        for (MultipartFile file : files) {
            Map<String, String> fileInfo = new LinkedHashMap<>();
            fileInfo.put("originalName", file.getOriginalFilename());
            fileInfo.put("size", formatFileSize(file.getSize()));
            fileInfo.put("contentType", file.getContentType());
            
            if (file.isEmpty()) {
                fileInfo.put("status", "failed");
                fileInfo.put("error", "Empty file");
                failedFiles.add(fileInfo);
                continue;
            }
            
            try {
                // Generate a unique filename
                String originalFilename = file.getOriginalFilename();
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
                String newFilename = timestamp + "_" + originalFilename;
                
                // Save file locally
                Path filePath = Paths.get(UPLOAD_DIR, newFilename);
                Files.copy(file.getInputStream(), filePath);
                
                fileInfo.put("status", "success");
                fileInfo.put("savedAs", newFilename);
                fileInfo.put("path", filePath.toAbsolutePath().toString());
                processedFiles.add(fileInfo);
                
            } catch (IOException e) {
                fileInfo.put("status", "failed");
                fileInfo.put("error", e.getMessage());
                failedFiles.add(fileInfo);
            }
        }
        
        // Build response
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("success", failedFiles.isEmpty());
        response.put("totalFiles", files.length);
        response.put("successfulUploads", processedFiles.size());
        response.put("failedUploads", failedFiles.size());
        response.put("message", String.format("Processed %d file(s) successfully. %s", 
            processedFiles.size(),
            failedFiles.isEmpty() ? "" : String.format("Failed to process %d file(s).", failedFiles.size()))
        );
        
        if (!processedFiles.isEmpty()) {
            response.put("processedFiles", processedFiles);
        }
        
        if (!failedFiles.isEmpty()) {
            response.put("failedFiles", failedFiles);
        }
        
        // Set the appropriate message
        if (processedFiles.isEmpty() && !failedFiles.isEmpty()) {
            response.put("message", "All file uploads failed");
            return ResponseEntity.status(400).body(response);
        } else if (!processedFiles.isEmpty() && !failedFiles.isEmpty()) {
            response.put("message", "Some files were not uploaded successfully");
            return ResponseEntity.status(207).body(response);
        } else {
            response.put("message", "All files were uploaded successfully");
            return ResponseEntity.ok(response);
        }
    }
    
    private String formatFileSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.1f %s", 
            size / Math.pow(1024, digitGroups), 
            units[digitGroups]);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("File upload test endpoint is working! Current time: " + 
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
