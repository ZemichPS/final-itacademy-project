package by.itacademy.sharedresource.core.dto;


import by.itacademy.sharedresource.core.enums.EssenceType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuditRecord(
        UUID uuid,
        LocalDateTime dtCreate,
        UserAudit userAudit,
        String text,
        EssenceType type,
        String id
){};


