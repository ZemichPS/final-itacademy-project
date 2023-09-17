package by.ITAcademy.taskservice.sevice.api;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;

public interface IAuditService {

    void saveEvent(AuditEventRecord record);
}
