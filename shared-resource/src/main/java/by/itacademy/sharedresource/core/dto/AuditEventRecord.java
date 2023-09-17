package by.itacademy.sharedresource.core.dto;


import by.itacademy.sharedresource.core.enums.EssenceType;
import by.itacademy.sharedresource.core.enums.UserRole;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuditEventRecord(
        LocalDateTime dtCreate,
        String text,
        EssenceType type,
        String id,
        UUID actorUuid,
        String actorMail,
        String actorFullName,
        UserRole actorRole
) {
};


