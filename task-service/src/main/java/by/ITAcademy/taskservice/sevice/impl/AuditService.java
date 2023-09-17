package by.ITAcademy.taskservice.sevice.impl;


import by.ITAcademy.taskservice.sevice.api.IAuditService;
import by.ITAcademy.taskservice.sevice.api.IProducerEventService;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import org.springframework.stereotype.Service;

@Service
public class AuditService implements IAuditService {

    private final IProducerEventService producerEventService;

    public AuditService(IProducerEventService producerEventService) {
        this.producerEventService = producerEventService;
    }

    @Override
    public void saveEvent(AuditEventRecord record) {
        producerEventService.saveEvent(record);
    }
}
