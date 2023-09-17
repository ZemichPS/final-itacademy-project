package by.ITAcademy.UserMicroService.core.exception;

public class IncorrectPasswordException extends IllegalArgumentException{
    public IncorrectPasswordException(String s) {
        super(s);
    }

    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
