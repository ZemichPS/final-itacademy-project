package by.ITAcademy.UserMicroService.services.api;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;

public interface IProducerEventService {
    void saveEvent(AuditEventRecord record);
}
