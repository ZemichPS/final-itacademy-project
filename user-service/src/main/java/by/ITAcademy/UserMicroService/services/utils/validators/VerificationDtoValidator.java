package by.ITAcademy.UserMicroService.services.utils.validators;

import by.ITAcademy.UserMicroService.core.DTO.VerificationDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class VerificationDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return VerificationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VerificationDto verificationDto = (VerificationDto) target;
        FieldValidator fieldValidator = new FieldValidator(errors);
        fieldValidator.checkOnEmail(verificationDto.mail());
        fieldValidator.checkOnVerificationCode(verificationDto.code());


    }
}
