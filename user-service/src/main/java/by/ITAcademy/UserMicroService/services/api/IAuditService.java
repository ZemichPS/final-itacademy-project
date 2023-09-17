package by.ITAcademy.UserMicroService.services.api;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;

public interface IAuditService {

    void saveEvent(AuditEventRecord record);
}
