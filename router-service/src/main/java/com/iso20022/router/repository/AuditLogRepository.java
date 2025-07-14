package com.iso20022.router.repository;

import com.iso20022.router.model.AuditLog;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AuditLogRepository extends CouchbaseRepository<AuditLog, String> {
    
    List<AuditLog> findByMessageId(String messageId);
    
    List<AuditLog> findByStatus(String status);
    
    List<AuditLog> findBySender(String sender);
    
    List<AuditLog> findByReceiver(String receiver);
    
    List<AuditLog> findByMessageType(String messageType);
    
    List<AuditLog> findByTimestampBetween(Instant start, Instant end);
}
