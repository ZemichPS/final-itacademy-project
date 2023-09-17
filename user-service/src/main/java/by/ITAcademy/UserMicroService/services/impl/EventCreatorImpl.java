package by.ITAcademy.UserMicroService.services.impl;

import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import by.ITAcademy.UserMicroService.dao.api.IUserDAO;
import by.ITAcademy.UserMicroService.services.api.IEventCreator;
import by.ITAcademy.UserMicroService.services.api.IUserHolder;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.dto.UserAudit;
import by.itacademy.sharedresource.core.enums.EssenceType;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventCreatorImpl implements IEventCreator {
    private final IUserHolder userHolder;
    private final IUserDAO userRepository;
    private final ConversionService conversionService;


    public EventCreatorImpl(IUserHolder userHolder,
                            IUserDAO userRepository,
                            ConversionService conversionService) {
        this.userHolder = userHolder;
        this.userRepository = userRepository;
        this.conversionService = conversionService;

    }

    @Override
    public AuditEventRecord create(EssenceType type, String text, String id) {
        UserDetails userDetails = userHolder.getUser();
        UUID userUuid = UUID.fromString(userDetails.getUsername());
        UserDto user = conversionService.convert(userRepository.findById(userUuid).get(), UserDto.class);


        AuditEventRecord eventRecord = new AuditEventRecord(
                LocalDateTime.now(),
                text,
                type,
                id,
                user.getUuid(),
                user.getMail(),
                user.getFullName(),
                user.getRole()
                );

        return eventRecord;
    }

    @Override
    public AuditEventRecord create(UserDto userDto, EssenceType type, String text, String id) {


        AuditEventRecord eventRecord = new AuditEventRecord(
                LocalDateTime.now(),
                text,
                type,
                id,
                userDto.getUuid(),
                userDto.getMail(),
                userDto.getFullName(),
                userDto.getRole()
        );
        return eventRecord;

    }
}
