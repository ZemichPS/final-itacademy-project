package by.ITAcademy.UserMicroService.core.exception;

public class FailedInSendingEmailException extends RuntimeException{
    public FailedInSendingEmailException() {
        super();
    }

    public FailedInSendingEmailException(String message) {
        super(message);
    }

    public FailedInSendingEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
