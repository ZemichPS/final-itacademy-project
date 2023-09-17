package by.ITAcademy.taskservice.sevice.impl;

import by.ITAcademy.taskservice.core.dto.UserActor;

import by.ITAcademy.taskservice.sevice.api.IEventCreator;
import by.ITAcademy.taskservice.sevice.api.IUserHolder;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.enums.EssenceType;

import by.itacademy.sharedresource.core.dto.UserAudit;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class EventCreatorImpl implements IEventCreator {
    private final IUserHolder userHolder;

    public EventCreatorImpl(IUserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @Override
    public AuditEventRecord create(EssenceType type, String text, String id) {
        UserActor actor = userHolder.getUser();
        UserAudit userAudit = new UserAudit(
                actor.getUuid(),
                actor.getMail(),
                actor.getFullName(),
                actor.getRole()
        );

        return new AuditEventRecord(
                LocalDateTime.now(),
                userAudit,
                text,
                type,
                id
        );
    }
}
