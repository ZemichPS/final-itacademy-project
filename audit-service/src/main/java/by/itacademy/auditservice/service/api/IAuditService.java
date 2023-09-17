package by.itacademy.auditservice.service.api;

import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IAuditService {
    Page<AuditEntity> get(PageRequest pageRequest);
    AuditEntity get(UUID uuid);
    AuditEntity save(AuditEventRecord record);

}
