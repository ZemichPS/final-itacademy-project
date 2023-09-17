package by.ITAcademy.UserMicroService.services.utils.converters;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.UserCreateDto;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class UserCreateDtoToUserEntityConverter implements Converter<UserCreateDto, UserEntity> {
    @Override
    public UserEntity convert(UserCreateDto userCreateDto) {
        return  new UserEntity(
                UUID.randomUUID(),
                userCreateDto.mail(),
                userCreateDto.fullName(),
                userCreateDto.role(),
                userCreateDto.status(),
                null
        );

    }
}
