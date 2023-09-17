package by.ITAcademy.taskservice.sevice.api;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;

public interface IProducerEventService {
    void saveEvent(AuditEventRecord record);
}
