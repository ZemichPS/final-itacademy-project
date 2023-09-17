package by.ITAcademy.UserMicroService.services.utils.validators;

import by.ITAcademy.UserMicroService.core.DTO.UserRegistrationDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserRegistrationDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationDto userRegistrationDto = (UserRegistrationDto)  target;
        FieldValidator fieldValidator = new FieldValidator(errors);

        fieldValidator.checkOnEmail(userRegistrationDto.mail());
        fieldValidator.checkOnFullName(userRegistrationDto.fullName());
        fieldValidator.checkOnPassword(userRegistrationDto.password());

    }
}
