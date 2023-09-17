package by.itacademy.sharedresource.core.dto;

import by.itacademy.sharedresource.core.enums.UserRole;

import java.io.Serializable;
import java.util.UUID;

public record UserAudit(UUID uuid,
                        String mail,
                        String fio,
                        UserRole role) {}
