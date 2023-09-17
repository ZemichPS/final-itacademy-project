package by.ITAcademy.UserMicroService.services.utils.validators;

import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);

    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        FieldValidator fieldValidator = new FieldValidator(errors);

        fieldValidator.checkOnEmail(userDto.getMail());
        fieldValidator.checkOnFullName(userDto.getFullName());
        fieldValidator.checkOnStatus(userDto.getStatus());
        fieldValidator.checkOnRole(userDto.getRole());
    }
}
