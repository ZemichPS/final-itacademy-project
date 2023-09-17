package by.ITAcademy.UserMicroService.services.utils.validators;

import by.ITAcademy.UserMicroService.core.DTO.UserCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserCreateDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreateDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDto userCreateDto = (UserCreateDto) target;
        FieldValidator fieldValidator = new FieldValidator(errors);

        fieldValidator.checkOnEmail(userCreateDto.mail());
        fieldValidator.checkOnFullName(userCreateDto.fullName());
        fieldValidator.checkOnPassword(userCreateDto.password());
        fieldValidator.checkOnRole(userCreateDto.role());
        fieldValidator.checkOnStatus(userCreateDto.status());
    }
}
