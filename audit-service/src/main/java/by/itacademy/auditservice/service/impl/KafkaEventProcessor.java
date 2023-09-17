package by.itacademy.auditservice.service.impl;

import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.auditservice.service.api.IAuditService;
import by.itacademy.auditservice.service.api.IEventProcessor;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class KafkaEventProcessor implements IEventProcessor<AuditEventRecord> {

    private final IAuditService auditService;

    public KafkaEventProcessor(IAuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public void process(AuditEventRecord record) {
        auditService.save(record);

    }
}
