package by.ITAcademy.UserMicroService.services.utils.converters;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.UserUpdateDto;
import org.springframework.core.convert.converter.Converter;

public class UserUpdateDtoToUserEntityConverter implements Converter<UserUpdateDto, UserEntity> {
    @Override
    public UserEntity convert(UserUpdateDto source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setMail(source.mail());
        userEntity.setFullName(source.fullName());
        userEntity.setPassword(source.password());
        userEntity.setRole(source.role());
        userEntity.setStatus(source.status());
        return userEntity;
    }
}
