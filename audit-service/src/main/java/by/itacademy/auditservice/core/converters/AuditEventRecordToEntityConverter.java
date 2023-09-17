package by.itacademy.auditservice.core.converters;

import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.auditservice.dao.entity.UserEntity;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.dto.UserAudit;
import org.springframework.core.convert.converter.Converter;

public class AuditEventRecordToEntityConverter implements Converter<AuditEventRecord, AuditEntity> {
    @Override
    public AuditEntity convert(AuditEventRecord source) {
        UserEntity user = new UserEntity();
        user.setUuid(source.actorUuid());
        user.setMail(source.actorMail());
        user.setFio(source.actorFullName());
        user.setRole(source.actorRole());

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setType(source.type());
        auditEntity.setText(source.text());
        auditEntity.setId(source.id());
        auditEntity.setUser(user);
        return auditEntity;
    }
}
