package by.ITAcademy.UserMicroService.services.impl;



import by.ITAcademy.UserMicroService.services.api.IAuditService;
import by.ITAcademy.UserMicroService.services.api.IProducerEventService;
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
