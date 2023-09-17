package by.itacademy.auditservice.core.converters;

import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.auditservice.dao.entity.UserEntity;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import org.springframework.core.convert.converter.Converter;

public class AuditRecordToEntityConverter implements Converter<AuditRecord, AuditEntity> {
    @Override
    public AuditEntity convert(AuditRecord source) {
        UserEntity user = new UserEntity();
        user.setUuid(source.userAudit().uuid());
        user.setFio(source.userAudit().fio());
        user.setMail(source.userAudit().mail());
        user.setRole(source.userAudit().role());
        AuditEntity entity = new AuditEntity();
        entity.setUser(user);
        entity.setText(source.text());
        entity.setType(source.type());
        entity.setId(source.id());
        return entity;
    }
}
