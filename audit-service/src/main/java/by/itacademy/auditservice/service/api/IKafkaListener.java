package by.itacademy.auditservice.service.api;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface IKafkaListener {
    public void listen(AuditEventRecord record);
}
