package by.ITAcademy.UserMicroService.services.utils.converters;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.UserRegistrationDto;
import org.springframework.core.convert.converter.Converter;

public class UserRegistrationDtoToUserEntityConverter implements Converter<UserRegistrationDto, UserEntity> {
    @Override
    public UserEntity convert(UserRegistrationDto source) {
        UserEntity entity = new UserEntity();
        entity.setMail(source.mail());
        entity.setFullName(source.fullName());
        entity.setPassword(source.password());
        return entity;
    }
}
