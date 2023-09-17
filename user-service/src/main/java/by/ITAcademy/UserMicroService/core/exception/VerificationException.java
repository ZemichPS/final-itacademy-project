package by.ITAcademy.UserMicroService.core.exception;

public class VerificationException extends IllegalArgumentException {
    public VerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationException(String message) {
        super(message);
    }

}
