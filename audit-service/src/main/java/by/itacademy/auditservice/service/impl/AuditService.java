package by.itacademy.auditservice.service.impl;

import by.itacademy.auditservice.core.exceptions.FindEntityException;
import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.auditservice.dao.repositories.IAuditRepository;
import by.itacademy.auditservice.service.api.IAuditService;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@Log4j2
public class AuditService implements IAuditService {
    private static final String ERROR_GET_RESPONSE = "Failed to get audit. Try again or contact support!";
    private static final String USER_NOT_EXIST_RESPONSE = "Audit with this id does not exist!";
    private final IAuditRepository auditRepository;
    private final ConversionService conversionService;

    public AuditService(IAuditRepository auditRepository, ConversionService conversionService) {
        this.auditRepository = auditRepository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditEntity> get(PageRequest pageRequest) {
        try {
            return auditRepository.findAll(pageRequest);
        } catch (DataAccessException ex) {
            throw new FindEntityException(ERROR_GET_RESPONSE, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuditEntity get(UUID uuid) {
        return auditRepository.findById(uuid)
                .orElseThrow(() -> new FindEntityException(USER_NOT_EXIST_RESPONSE));
    }

    @Override
    public AuditEntity save(AuditEventRecord record) {

        AuditEntity entity = conversionService.convert(record, AuditEntity.class);
        entity.setUuid(UUID.randomUUID());
        System.out.println(entity);
        return auditRepository.save(entity);
    }

}



