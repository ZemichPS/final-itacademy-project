package by.ITAcademy.UserMicroService.core.exception;


import org.springframework.validation.ObjectError;
import java.util.List;

public class InvalidDtoException extends RuntimeException{

    private List<ObjectError> errors;

    public InvalidDtoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDtoException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
