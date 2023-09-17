package by.ITAcademy.taskservice.sevice.api;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.enums.EssenceType;

public interface IEventCreator {
    AuditEventRecord create(EssenceType type, String text, String id);
}
