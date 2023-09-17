package by.ITAcademy.UserMicroService.core.exception;

public class FailedInActivatingException extends RuntimeException{
    public FailedInActivatingException(String failedInSendingEmail, Throwable cause) {
    }

    public FailedInActivatingException(String message) {
        super(message);
    }
}
