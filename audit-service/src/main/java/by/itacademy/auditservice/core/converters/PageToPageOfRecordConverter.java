package by.itacademy.auditservice.core.converters;


import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import by.itacademy.sharedresource.core.dto.PageDTO;

import by.itacademy.sharedresource.core.dto.UserAudit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class PageToPageOfRecordConverter implements Converter<Page<AuditEntity>, PageDTO<AuditRecord>> {
    @Override
    public PageDTO<AuditRecord> convert(Page<AuditEntity> source) {

        PageDTO<AuditRecord> recordsPage = new PageDTO<>();
        recordsPage.setNumber(source.getNumber());
        recordsPage.setSize(source.getSize());
        recordsPage.setTotalPage(source.getTotalPages());
        recordsPage.setTotalElements(source.getTotalElements());
        recordsPage.setFirst(source.isFirst());
        recordsPage.setNumberOfElements(source.getNumberOfElements());
        recordsPage.setLast(source.isLast());
        source.getContent().stream().map(entity -> new AuditRecord(
                entity.getUuid(),
                entity.getDtCreate(),
                new UserAudit(
                        entity.getUser().getUuid(),
                        entity.getUser().getMail(),
                        entity.getUser().getFio(),
                        entity.getUser().getRole()
                ),
                entity.getText(),
                entity.getType(),
                entity.getId()
        ));

        return recordsPage;
    }

}


