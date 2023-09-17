package by.ITAcademy.UserMicroService.services.utils.validators;

import by.itacademy.sharedresource.core.enums.UserRole;
import by.itacademy.sharedresource.core.enums.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FieldValidator {
    private Errors errors;

    public FieldValidator(Errors errors) {
        this.errors = errors;
    }

    public FieldValidator() {
    }

    public void checkOnEmail(String email) {
        Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()) {
            errors.rejectValue("mail", "mail", "mail address is invalid");
        }
    }

    public void checkOnFullName(String fullName){
        if (fullName.equals("")){
            errors.rejectValue("fullName", "fio", "field fio cannot be empty");
        } else if (fullName.length() < 6){
            errors.rejectValue("fullName", "fio", "the fio length must be as minimum 6 characters");
        }
    }

    public void checkOnPassword(String password){
        if (password.equals("")){
            errors.rejectValue("password", "password", "password cannot be empty");
        } else if (password.length() < 6){
            errors.rejectValue("password", "password", "the password length must be as minimum 6 characters");
        }
    }

    public void checkOnRole(UserRole role){

        if(role.name().isEmpty()){
            errors.rejectValue("role", "role", "role is required");
        }
    }

    public void checkOnStatus(UserStatus status){

        if(status.name().isEmpty()){
            errors.rejectValue("status", "status", "status is required");
        }
    }

    public void checkOnVerificationCode (String code){
        if (code.equals("")){
            errors.rejectValue("password", "password", "password cannot be empty");
        } else if (code.length() < 4){
            errors.rejectValue("password", "password", "the password length must contains 4 characters");
        }
    }
}
