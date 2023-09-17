package by.ITAcademy.UserMicroService.services.utils.converters;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;



public class UserEntityToUserDtoConverter implements Converter<UserEntity, UserDto> {


    @Override
    public UserDto convert(UserEntity entity) {
        UserDto userDTO = new UserDto();
        userDTO.setUuid(entity.getUuid());
        userDTO.setCreatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getDtCreate().getTime()), ZoneId.systemDefault()));
        userDTO.setUpdatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getDtUpdate().getTime()), ZoneId.systemDefault()));
        userDTO.setMail(entity.getMail());
        userDTO.setFullName(entity.getFullName());
        userDTO.setRole(entity.getRole());
        userDTO.setStatus(entity.getStatus());
        return userDTO;
    }


}
