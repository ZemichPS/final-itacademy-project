package by.itacademy.auditservice.core.converters;


import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import by.itacademy.sharedresource.core.dto.UserAudit;

import org.springframework.core.convert.converter.Converter;

import java.time.ZoneId;

public class AuditEntityToAuditRecordConverter implements Converter<AuditEntity, AuditRecord> {
    @Override
    public AuditRecord convert(AuditEntity source) {
        AuditRecord record = new AuditRecord(
                source.getUuid(),
                source.getDtCreate(),
                new UserAudit(
                        source.getUser().getUuid(),
                        source.getUser().getMail(),
                        source.getUser().getFio(),
                        source.getUser().getRole()
                ),
                source.getText(),
                source.getType(),
                source.getId()
        );
        return record;
    }


}
