package by.ITAcademy.UserMicroService.services.api;


import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.enums.EssenceType;

import java.util.UUID;

public interface IEventCreator {
    AuditEventRecord create(EssenceType type, String text, String id);

    AuditEventRecord create(UserDto user, EssenceType type, String text, String id);
}
